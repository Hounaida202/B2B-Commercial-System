package com.example.demo.DTOs.Requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;
@Data
public class CommandeRequestDTO {

    private List<CommandeProduitRequestDTO> produits;
    @Nullable
    @Pattern(regexp =
            "PROMO-[A-Z0-9]{4}",
    message="ce code promo n est pas valide ")
    private String codePromo;

}

