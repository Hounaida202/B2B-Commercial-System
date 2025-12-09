package com.example.demo.mappers;

import com.example.demo.DTOs.Requests.ClientDTO;
import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.DTOs.Responses.ClientResponseDTO;
import com.example.demo.entities.Client;
import com.example.demo.entities.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring", uses= {UserMapper.class})

public interface ClientMapper {


    @Mapping(target = "user", ignore = true)
    Client toEntity(ClientDTO clientDto);
    ClientResponseDTO toDTO(Client client);
}
