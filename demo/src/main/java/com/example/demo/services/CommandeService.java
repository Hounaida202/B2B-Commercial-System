package com.example.demo.services;


import com.example.demo.DTOs.Requests.CommandeRequestDTO;
import com.example.demo.DTOs.Requests.CommandeProduitRequestDTO;
import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.DTOs.Responses.CommandeResponseDTO;
import com.example.demo.entities.Client;
import com.example.demo.entities.Commande;
import com.example.demo.entities.CommandeProduit;
import com.example.demo.entities.Produit;
import com.example.demo.enums.CustomerTier;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.CommandeMapper;
import com.example.demo.mappers.CommandeProduitMapper;
import com.example.demo.mappers.ProduitMapper;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.repositories.ProduitRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private ProduitMapper produitMapper;
    @Autowired
    private CommandeMapper commandeMapper;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CalculesService calculesService;
    @Autowired
    private CommandeProduitMapper commandeProduitMapper;


    public CommandeResponseDTO creerCommande(CommandeRequestDTO commandeRequestDTO , HttpSession masession){
        Commande commande = commandeMapper.toEntity(commandeRequestDTO);
        commande.setDate(LocalDate.now());
        Long idClient = (Long) masession.getAttribute("clientID");
        if (idClient == null) {
            throw new NotFoundException("Client non connecté ou ID manquant dans la session");
        }

        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new NotFoundException("Client non trouvé"));

        commande.setClient(client);

        commande.setOrderStatus(OrderStatus.PENDING);
        List<CommandeProduit> listItems = new ArrayList<>();
         for(CommandeProduitRequestDTO item : commandeRequestDTO.getProduits()){

             Produit produit = produitRepository.findById(item.getProduitId()).orElseThrow(()->new NotFoundException("produit non trouvé"));
             CommandeProduit cp = new CommandeProduit();
             cp.setQuantite(item.getQuantite());
             cp.setProduit(produit);
             cp.setCommande(commande);
             cp.setPrixUnitaire(produit.getPrix_unitaire());
             cp.setTotalLigne(produit.getPrix_unitaire() * item.getQuantite());
             listItems.add(cp);
         }

         commande.setCommande_produit(listItems);
         commande.setTotal(calculesService.calculerTotal(commande));
         commande.setRemise(calculesService.calculerRemise(client , commande));
         commande.setSousTotal(calculesService.calculerSousTotal(client,commande));
         commande.setTva(calculesService.calculerTVA(calculesService.calculerTotal(commande)));
         commande.setMontantRestant(calculesService.calculerTotal(commande));
         commandeRepository.save(commande);


          CommandeResponseDTO commandeResponseDTO = commandeMapper.toDTO(commande);

          return commandeResponseDTO;
    }



    public Double calculerMontantCume(Commande commande) {
        Client client = commande.getClient();

        List<Commande> commandesConfirmees = commandeRepository.findByClientIdAndOrderStatus(client.getId(),OrderStatus.CONFIRMED);
        return commandesConfirmees.stream()
                .mapToDouble(Commande::getTotal)
                .sum();
    }


    @Transactional
    public CommandeResponseDTO confirmerCommande(Long commandeId) {

        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new NotFoundException("Commande introuvable"));

        commande.setOrderStatus(OrderStatus.CONFIRMED);
        commandeRepository.save(commande);

        Client client = commande.getClient();

        int confirmedCount = commandeRepository.countByClientIdAndOrderStatus(
                client.getId(),
                OrderStatus.CONFIRMED
        );

        Double totalCumule = calculerMontantCume(commande);
        if (totalCumule == null) totalCumule = 0.0;

        if (confirmedCount >= 20 || totalCumule >= 15000) {
            client.setCustomerTier(CustomerTier.PLATINUM);
        } else if (confirmedCount >= 10 || totalCumule >= 5000) {
            client.setCustomerTier(CustomerTier.GOLD);
        } else if (confirmedCount >= 3 || totalCumule >= 1000) {
            client.setCustomerTier(CustomerTier.SILVER);
        } else {
            client.setCustomerTier(CustomerTier.BASIC);
        }

        double sousTotal = commande.getSousTotal();
        double remise = 0.0;

        switch (client.getCustomerTier()) {
            case SILVER -> { if (sousTotal >= 500) remise = sousTotal * 0.05; }
            case GOLD -> { if (sousTotal >= 800) remise = sousTotal * 0.10; }
            case PLATINUM -> { if (sousTotal >= 1200) remise = sousTotal * 0.15; }
            default -> remise = 0.0;
        }

        commande.setRemise(remise);
        commande.setTotal(sousTotal - remise);
        clientRepository.save(client);
        commandeRepository.save(commande);
        return commandeMapper.toDTO(commande);
    }







}
