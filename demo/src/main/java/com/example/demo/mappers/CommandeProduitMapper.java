package com.example.demo.mappers;

import com.example.demo.DTOs.Requests.CommandeProduitRequestDTO;
import com.example.demo.DTOs.Responses.CommandeProduitResponseDTO;
import com.example.demo.entities.CommandeProduit;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
//public interface CommandeProduitMapper {
//
//    @Mapping(target = "commande", ignore = true)
//    @Mapping(target = "produit", ignore = true)
//    @Mapping(target = "prixUnitaire", ignore = true)
//    @Mapping(target = "totalLigne", ignore = true)
//    CommandeProduit toEntity(CommandeProduitRequestDTO dto);
//
//
//    CommandeProduitResponseDTO toDTO(CommandeProduit cp);
//
//}

@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
public interface CommandeProduitMapper {

    // Pour créer l'entité depuis le DTO request
    @Mapping(target = "commande", ignore = true)
    @Mapping(target = "produit", ignore = true) // on ignore ici si le DTO n'a pas l'objet produit complet
    @Mapping(target = "prixUnitaire", ignore = true)
    @Mapping(target = "totalLigne", ignore = true)
    CommandeProduit toEntity(CommandeProduitRequestDTO dto);

    // Pour retourner le DTO response
    @Mapping(target = "produit", source = "produit") // ← IMPORTANT : MapStruct utilisera ProduitMapper
    CommandeProduitResponseDTO toDTO(CommandeProduit cp);
}


