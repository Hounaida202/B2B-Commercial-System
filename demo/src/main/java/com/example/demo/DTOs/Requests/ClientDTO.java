package com.example.demo.DTOs.Requests;

import com.example.demo.enums.CustomerTier;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ClientDTO {
    @NotBlank(message="le nom est obligatoire")
    private String nom;

    @Column(nullable = true)
    private CustomerTier customerTier;

    @NotNull(message="les infos email et password sont obligatoires")
    @Valid
    private UserDTO user;
}