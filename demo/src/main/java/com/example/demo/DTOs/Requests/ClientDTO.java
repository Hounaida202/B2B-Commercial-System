package com.example.demo.DTOs.Requests;

import com.example.demo.enums.CustomerTier;
import lombok.Data;


@Data
public class ClientUpdateDTO {
    private String nom;
    private CustomerTier customerTier;
    private UserUpdateDTO user;
}
