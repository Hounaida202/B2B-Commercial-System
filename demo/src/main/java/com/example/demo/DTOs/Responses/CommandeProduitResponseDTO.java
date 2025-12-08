package com.example.demo.DTOs.Responses;

import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.entities.Produit;
import lombok.Data;

@Data
public class CommandeProduitResponseDTO {
    private ProduitResponseDTO produit;

    private int quantite;
}

