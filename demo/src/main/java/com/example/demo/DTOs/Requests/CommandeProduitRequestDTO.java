package com.example.demo.DTOs.Requests;

import lombok.Data;

@Data
public class CommandeProduitRequestDTO {
    private Long produitId;
    private int quantite;
}
