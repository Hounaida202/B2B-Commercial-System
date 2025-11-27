package com.example.demo.services;

import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.entities.Produit;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.ProduitMapper;
import com.example.demo.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public Page<ProduitDTO> recuperProduitsAvecPaginationEtFilter(
            String nom,
            Integer stock,
            Pageable pagination
    ) {
        Page<Produit> result;

        if (nom != null && stock != null) {
            result = produitRepository.findByNomContainingIgnoreCaseAndStock(
                    nom, stock, pagination
            );
        }
        else if (nom != null) {
            result = produitRepository.findByNomContainingIgnoreCase(nom, pagination);
        }
        else if (stock != null) {
            result = produitRepository.findByStock(stock, pagination);
        }
        else {
            result = produitRepository.findAll(pagination);
        }

        if (result.isEmpty()) {
            throw new NotFoundException("pas de produit trouvé ");
        }

        Page<ProduitDTO> list = result.map(produitMapper::toDTO);


        return list;
    }



}
