package com.example.demo.mappers;

import com.example.demo.DTOs.Requests.PaiementRequestDTO;
import com.example.demo.DTOs.Responses.PaiementResponseDTO;
import com.example.demo.entities.Paiement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaiementMapper {

    @Mapping(target = "commande", ignore = true)
    @Mapping(target = "datePaiement", ignore = true)
    @Mapping(target = "statut", ignore = true)
    Paiement toEntity(PaiementRequestDTO dto);

    PaiementResponseDTO toDTO(Paiement paiement);
}