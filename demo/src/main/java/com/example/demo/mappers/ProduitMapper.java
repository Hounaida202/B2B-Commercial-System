package com.example.demo.mappers;

import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.DTOs.Responses.ProduitResponseDTO;
import com.example.demo.entities.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ProduitMapper {
    Produit toEntity(ProduitDTO produitDto);
    ProduitResponseDTO toDTO(Produit produit);
}
