package com.example.demo.DTOs.Responses;

import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.PaymentStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PaiementResponseDTO {

    private Long id;
    private Double montant;
    private PaymentMethod moyenPaiement;
    private String reference;
    private String banque;
    private LocalDate dateEcheance;
    private LocalDate datePaiement;
    private PaymentStatus statut;
}