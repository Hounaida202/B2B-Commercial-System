package com.example.demo.DTOs.Responses;

import com.example.demo.entities.User;
import com.example.demo.enums.CustomerTier;
import lombok.Data;

@Data
public class ClientResponseDTO {

    private String nom;

    private CustomerTier customerTier;

    private UserResponseDTO user;

}
