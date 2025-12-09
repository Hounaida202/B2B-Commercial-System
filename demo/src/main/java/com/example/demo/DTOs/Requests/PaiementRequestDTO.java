package com.example.demo.DTOs.Requests;

import com.example.demo.enums.PaymentMethod;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PaiementRequestDTO {

    @NotNull(message = "Le montant est obligatoire")
    @Min(value = 1, message = "Le montant doit être supérieur à 0")
    private Double montant;

    @NotNull(message = "Le moyen de paiement est obligatoire")
    private PaymentMethod moyenPaiement;

    @NotNull(message = "La référence est obligatoire")
    private String reference;

    @Nullable
    private String banque;

    @Nullable
    private LocalDate dateEcheance;
}