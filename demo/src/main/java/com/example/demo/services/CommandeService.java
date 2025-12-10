/*

package com.example.demo.services;

import com.example.demo.DTOs.Requests.CommandeRequestDTO;
import com.example.demo.DTOs.Requests.CommandeProduitRequestDTO;
import com.example.demo.DTOs.Responses.CommandeResponseDTO;
import com.example.demo.entities.Client;
import com.example.demo.entities.Commande;
import com.example.demo.entities.CommandeProduit;
import com.example.demo.entities.Produit;
import com.example.demo.enums.CustomerTier;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.CommandeMapper;
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

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeMapper commandeMapper;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CalculesService calculesService;


    @Transactional
    public CommandeResponseDTO creerCommande(CommandeRequestDTO dto, HttpSession session) {


        Long idClient = (Long) session.getAttribute("clientID");
        if (idClient == null) {
            throw new NotFoundException("Vous devez être connecté ");
        }
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new NotFoundException("Client non trouvé"));




        for (CommandeProduitRequestDTO item : dto.getProduits()) {
            if (item.getQuantite() <= 0) {
                throw new IllegalArgumentException("La quantité doit être supérieure à 0");
            }

            Produit produit = produitRepository.findById(item.getProduitId())
                    .orElseThrow(() -> new NotFoundException("Produit non trouvé : ID " + item.getProduitId()));
            if (produit.getStock() < item.getQuantite()) {
                throw new IllegalArgumentException(
                        "Stock insuffisant pour " + produit.getNom());
            }
        }



        Commande commande = new Commande();
        commande.setDate(LocalDate.now());
        commande.setClient(client);
        commande.setOrderStatus(OrderStatus.PENDING);
        commande.setCodePromo(dto.getCodePromo());

        List<CommandeProduit> listItems = new ArrayList<>();
        for (CommandeProduitRequestDTO item : dto.getProduits()) {
            Produit produit = produitRepository.findById(item.getProduitId()).get();
            CommandeProduit cp = new CommandeProduit();
            cp.setQuantite(item.getQuantite());
            cp.setProduit(produit);
            cp.setCommande(commande);
            cp.setPrixUnitaire(produit.getPrix_unitaire());
            cp.setTotalLigne(produit.getPrix_unitaire() * item.getQuantite());
            listItems.add(cp);
        }
        commande.setCommande_produit(listItems);

        double sousTotal = calculesService.calculerSousTotal(commande);
        double remise = calculesService.calculerRemise(client, commande);
        double montantHTApresRemise = calculesService.calculerMontantHTApresRemise(commande, remise);
        double tva = calculesService.calculerTVA(montantHTApresRemise);
        double totalTTC = calculesService.calculerTotalTTC(montantHTApresRemise, tva);

        commande.setSousTotal(sousTotal);
        commande.setRemise(remise);
        commande.setTva(tva);
        commande.setTotal(totalTTC);
        commande.setMontantRestant(totalTTC);

        commandeRepository.save(commande);

        return commandeMapper.toDTO(commande);
    }


    @Transactional
    public CommandeResponseDTO confirmerCommande(Long commandeId) {

        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new NotFoundException("Commande introuvable"));

        if (commande.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException("Seules les commandes PENDING peuvent être confirmées");
        }

        for (CommandeProduit cp : commande.getCommande_produit()) {
            Produit produit = cp.getProduit();

            if (produit.getStock() < cp.getQuantite()) {
                throw new IllegalArgumentException("Stock insuffisant pour " + produit.getNom());
            }

            produit.setStock(produit.getStock() - cp.getQuantite());
            produitRepository.save(produit);
        }

        commande.setOrderStatus(OrderStatus.CONFIRMED);
        commandeRepository.save(commande);

        Client client = commande.getClient();

        int nbCommandesConfirmees = commandeRepository.countByClientIdAndOrderStatus(
                client.getId(),
                OrderStatus.CONFIRMED
        );

        Double montantCumule = calculerMontantCumule(client.getId());
        if (montantCumule == null) montantCumule = 0.0;

        if (nbCommandesConfirmees >= 20 || montantCumule >= 15000) {
            client.setCustomerTier(CustomerTier.PLATINUM);
        } else if (nbCommandesConfirmees >= 10 || montantCumule >= 5000) {
            client.setCustomerTier(CustomerTier.GOLD);
        } else if (nbCommandesConfirmees >= 3 || montantCumule >= 1000) {
            client.setCustomerTier(CustomerTier.SILVER);
        } else {
            client.setCustomerTier(CustomerTier.BASIC);
        }

        clientRepository.save(client);

        return commandeMapper.toDTO(commande);
    }


    private Double calculerMontantCumule(Long clientId) {
        List<Commande> commandesConfirmees = commandeRepository
                .findByClientIdAndOrderStatus(clientId, OrderStatus.CONFIRMED);

        return commandesConfirmees.stream()
                .mapToDouble(Commande::getTotal)
                .sum();
    }




}
*/
package com.example.demo.services;

import com.example.demo.DTOs.Requests.CommandeRequestDTO;
import com.example.demo.DTOs.Requests.CommandeProduitRequestDTO;
import com.example.demo.DTOs.Responses.CommandeResponseDTO;
import com.example.demo.entities.Client;
import com.example.demo.entities.Commande;
import com.example.demo.entities.CommandeProduit;
import com.example.demo.entities.Produit;
import com.example.demo.enums.CustomerTier;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.CommandeMapper;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.repositories.ProduitRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.UnprocessableEntityException;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeMapper commandeMapper;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CalculesService calculesService;


    @Transactional
    public CommandeResponseDTO creerCommande(CommandeRequestDTO dto, HttpSession session) {

        Long idClient = (Long) session.getAttribute("clientID");
        if (idClient == null) {
            throw new NotFoundException("Vous devez être connecté ");
        }
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new NotFoundException("Client non trouvé"));

        for (CommandeProduitRequestDTO item : dto.getProduits()) {
            if (item.getQuantite() <= 0) {
                throw new IllegalArgumentException("La quantité doit être supérieure à 0");
            }

            Produit produit = produitRepository.findById(item.getProduitId())
                    .orElseThrow(() -> new NotFoundException("Produit non trouvé : ID " + item.getProduitId()));
            if (produit.getStock() < item.getQuantite()) {
                throw new UnprocessableEntityException("Stock insuffisant pour " + produit.getNom());
            }
        }

        Commande commande = new Commande();
        commande.setDate(LocalDate.now());
        commande.setClient(client);
        commande.setOrderStatus(OrderStatus.PENDING);
        commande.setCodePromo(dto.getCodePromo());

        List<CommandeProduit> listItems = new ArrayList<>();
        for (CommandeProduitRequestDTO item : dto.getProduits()) {
            Produit produit = produitRepository.findById(item.getProduitId()).get();
            CommandeProduit cp = new CommandeProduit();
            cp.setQuantite(item.getQuantite());
            cp.setProduit(produit);
            cp.setCommande(commande);
            cp.setPrixUnitaire(produit.getPrix_unitaire());
            cp.setTotalLigne(produit.getPrix_unitaire() * item.getQuantite());
            listItems.add(cp);
        }
        commande.setCommande_produit(listItems);

        double sousTotal = calculesService.calculerSousTotal(commande);
        double remise = calculesService.calculerRemise(client, commande);
        double montantHTApresRemise = calculesService.calculerMontantHTApresRemise(commande, remise);
        double tva = calculesService.calculerTVA(montantHTApresRemise);
        double totalTTC = calculesService.calculerTotalTTC(montantHTApresRemise, tva);

        commande.setSousTotal(sousTotal);
        commande.setRemise(remise);
        commande.setTva(tva);
        commande.setTotal(totalTTC);
        commande.setMontantRestant(totalTTC);

        commandeRepository.save(commande);

        return commandeMapper.toDTO(commande);
    }


    @Transactional
    public CommandeResponseDTO confirmerCommande(Long commandeId) {

        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new NotFoundException("Commande introuvable"));

        if (commande.getOrderStatus() != OrderStatus.PENDING) {
            throw new UnprocessableEntityException("Seules les commandes PENDING peuvent être confirmées");
        }

        if (commande.getMontantRestant() == null || commande.getMontantRestant() > 0) {
            throw new UnprocessableEntityException("La commande doit être totalement payée avant d'être confirmée");
        }

        for (CommandeProduit cp : commande.getCommande_produit()) {
            Produit produit = cp.getProduit();

            if (produit.getStock() < cp.getQuantite()) {
                throw new UnprocessableEntityException("Stock insuffisant pour " + produit.getNom());
            }

            produit.setStock(produit.getStock() - cp.getQuantite());
            produitRepository.save(produit);
        }

        commande.setOrderStatus(OrderStatus.CONFIRMED);
        commandeRepository.save(commande);

        Client client = commande.getClient();

        int nbCommandesConfirmees = commandeRepository.countByClientIdAndOrderStatus(
                client.getId(),
                OrderStatus.CONFIRMED
        );

        Double montantCumule = calculerMontantCumule(client.getId());
        if (montantCumule == null) montantCumule = 0.0;

        if (nbCommandesConfirmees >= 20 || montantCumule >= 15000) {
            client.setCustomerTier(CustomerTier.PLATINUM);
        } else if (nbCommandesConfirmees >= 10 || montantCumule >= 5000) {
            client.setCustomerTier(CustomerTier.GOLD);
        } else if (nbCommandesConfirmees >= 3 || montantCumule >= 1000) {
            client.setCustomerTier(CustomerTier.SILVER);
        } else {
            client.setCustomerTier(CustomerTier.BASIC);
        }

        clientRepository.save(client);

        return commandeMapper.toDTO(commande);
    }


    private Double calculerMontantCumule(Long clientId) {
        List<Commande> commandesConfirmees = commandeRepository
                .findByClientIdAndOrderStatus(clientId, OrderStatus.CONFIRMED);

        return commandesConfirmees.stream()
                .mapToDouble(Commande::getTotal)
                .sum();
    }

    public List<CommandeResponseDTO> recupererCommandes(Long clientId) {

        List<Commande> commandes = commandeRepository.findByClientId(clientId);

        List<CommandeResponseDTO> commandesDTO = commandes.stream()
                .map(commandeMapper::toDTO)
                .collect(Collectors.toList());

        return commandesDTO;
    }

    public List<CommandeResponseDTO> recupererMesCommande(HttpSession session) {


        Long idClient = (Long) session.getAttribute("clientID");
        if (idClient == null) {
         throw new BadRequestException("Client non connecté");
        }
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new NotFoundException("Client non trouvé"));

        List<Commande> commandes = commandeRepository.findByClientId(idClient);

        return commandes.stream()
                .map(commandeMapper::toDTO)
                .collect(Collectors.toList());
    }

}