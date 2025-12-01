package com.example.demo.services;


import com.example.demo.DTOs.Requests.CommandeRequestDTO;
import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.DTOs.Responses.CommandeResponseDTO;
import com.example.demo.entities.Client;
import com.example.demo.entities.Commande;
import com.example.demo.entities.CommandeProduit;
import com.example.demo.entities.Produit;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.CommandeMapper;
import com.example.demo.mappers.ProduitMapper;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.CommandeProduitRepository;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public CommandeResponseDTO creerCommande(CommandeRequestDTO commandeDto){

                Commande commande = commandeMapper.toEntity(commandeDto);
                commande.setOrderStatus(OrderStatus.PENDING);
                Client client =clientRepository.findById(commandeDto.getClientId()).orElseThrow(
                        ()->new NotFoundException("le client avec l id "+ commandeDto.getClientId()+ "n existe pas")
                );
                       commande.setClient(client);
                for(Long pId: commandeDto.getProduitids()){
                    Produit produit = produitRepository.findById(pId).orElseThrow(
                            ()->new NotFoundException("produit avec l id "+ pId + "n existe pas"));
                    CommandeProduit cp = new CommandeProduit();
                    cp.setCommande(commande);
                    cp.setProduit(produit);
                    commande.getCommande_produit().add(cp);

                }
                commandeRepository.save(commande);

                CommandeResponseDTO result = commandeMapper.toDTO(commande);
                result.setClientId(client.getId());

                List<ProduitDTO> produitsDTO = new ArrayList<>();

                for (CommandeProduit cp : commande.getCommande_produit()) {
                    ProduitDTO pDto = produitMapper.toDTO(cp.getProduit());
                    produitsDTO.add(pDto);
                }

                result.setProduits(produitsDTO);
                return result;
            }

}
