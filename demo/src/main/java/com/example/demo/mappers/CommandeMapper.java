//package com.example.demo.mappers;
//
//import com.example.demo.DTOs.Requests.CommandeRequestDTO;
//import com.example.demo.DTOs.Responses.CommandeResponseDTO;
//import com.example.demo.entities.Commande;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel="spring", uses= {ClientMapper.class, ProduitMapper.class})
//public interface CommandeMapper {
//
//
//    @Mapping(target="commande_produit" ,ignore=true)
//    @Mapping(target="client" ,ignore=true)
//    Commande toEntity(CommandeRequestDTO commandeRequestDto);
//
//    @Mapping(target="clientId" ,ignore=true)
//    CommandeResponseDTO toDTO(Commande commande);
//}
package com.example.demo.mappers;

import com.example.demo.DTOs.Requests.CommandeRequestDTO;
import com.example.demo.DTOs.Responses.CommandeResponseDTO;
import com.example.demo.entities.Commande;
import com.example.demo.entities.CommandeProduit;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//@Mapper(componentModel="spring", uses= {CommandeProduitMapper.class, ProduitMapper.class})
//
//public interface CommandeMapper {
//
//    @Mapping(target="commande_produit", source="produits")
//    @Mapping(target="client", ignore=true)
//    @Mapping(target="date", ignore=true)
//    @Mapping(target="sousTotal", ignore=true)
//    @Mapping(target="remise", ignore=true)
//    @Mapping(target="tva", ignore=true)
//    @Mapping(target="total", ignore=true)
//    @Mapping(target="orderStatus", ignore=true)
//    @Mapping(target="montantRestant", ignore=true)
//    Commande toEntity(CommandeRequestDTO dto);
//
//    @Mapping(target="codePromo", ignore=true)
//    CommandeResponseDTO toDTO(Commande commande);
//
//}

@Mapper(componentModel="spring", uses= {CommandeProduitMapper.class})
public interface CommandeMapper {

    @Mapping(target="commande_produit", source="produits") // produits dans CommandeRequestDTO → commande_produit dans Commande
    @Mapping(target="client", ignore=true)
    @Mapping(target="date", ignore=true)
    @Mapping(target="sousTotal", ignore=true)
    @Mapping(target="remise", ignore=true)
    @Mapping(target="tva", ignore=true)
    @Mapping(target="total", ignore=true)
    @Mapping(target="orderStatus", ignore=true)
    @Mapping(target="montantRestant", ignore=true)
    Commande toEntity(CommandeRequestDTO dto);

    @Mapping(target="produits", source="commande_produit") // ← MapStruct va mapper liste<CommandeProduit> → liste<CommandeProduitResponseDTO>
    CommandeResponseDTO toDTO(Commande commande);
}


