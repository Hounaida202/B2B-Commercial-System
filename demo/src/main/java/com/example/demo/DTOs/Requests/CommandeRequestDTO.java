package com.example.demo.DTOs.Requests;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;
@Data
public class CommandeRequestDTO {

    private List<CommandeProduitRequestDTO> produits;
    @Nullable
    private String codePromo;

}

