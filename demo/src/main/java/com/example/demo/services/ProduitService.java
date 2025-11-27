package com.example.demo.services;

import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.entities.Produit;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.ProduitMapper;
import com.example.demo.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProduitService {
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private ProduitMapper produitMapper;

    public ProduitDTO creerProduit(ProduitDTO produitDto){

          Produit produit = produitMapper.toEntity(produitDto);
         produitRepository.save(produit);
          return produitMapper.toDTO(produit);


    }

    public ProduitDTO modifierProduit(Long id , ProduitDTO produitDto){

        Produit produit = produitRepository.findById(id).orElseThrow(()->new NotFoundException("produit non trouver avec l id :"+ id));

        if(produitDto.getPrix_unitaire()!=null){
            produit.setPrix_unitaire(produitDto.getPrix_unitaire());
        }
        if(produitDto.getStock()!=null){
            produit.setStock(produitDto.getStock());
        }
        if(produitDto.getNom()!=null){
            produit.setNom(produitDto.getNom());
        }

        produitRepository.save(produit);

        return produitMapper.toDTO(produit);

    }
}
