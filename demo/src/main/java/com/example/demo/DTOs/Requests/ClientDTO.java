package com.example.demo.DTOs.Requests;

import com.example.demo.enums.CustomerTier;
import lombok.Data;


@Data
public class ClientDTO {
    private String nom;
    private CustomerTier customerTier;
    private UserDTO user;
}
