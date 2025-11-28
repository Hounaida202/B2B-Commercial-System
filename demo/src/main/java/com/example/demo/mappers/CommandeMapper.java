package com.example.demo.mappers;

import com.example.demo.DTOs.Requests.CommandeRequestDTO;
import com.example.demo.entities.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring", uses= {ClientMapper.class, ProduitMapper.class})
public interface CommandeMapper {


    @Mapping(target="commande_produit" ,ignore=true)
    @Mapping(target="client" ,ignore=true)

    Commande toEntity(CommandeRequestDTO commandeRequestDto);


    CommandeRequestDTO toDTO(Commande commande);
}
