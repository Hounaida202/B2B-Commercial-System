package com.example.demo.mappers;

import com.example.demo.DTOs.Requests.ClientDTO;
import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.entities.Client;
import com.example.demo.entities.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses= {UserMapper.class})

public interface ClientMapper {

    Client toEntity(ClientDTO clientDto);
    ClientDTO toDTO(Client client);
}
