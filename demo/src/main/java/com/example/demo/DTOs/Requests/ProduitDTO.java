package com.example.demo.DTOs.Requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProduitDTO {

    @Min(value = 1, message = "Le prix unitaire doit être > 0")
    private Double prix_unitaire;

    @Min(value = 0, message = "Le stock ne doit pas être négatif")
    private Integer stock;

    private String nom;
}