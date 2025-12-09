package com.example.demo.entities;

import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "paiements")
@Data
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    private Double montant;

    @Enumerated(EnumType.STRING)
    private PaymentMethod moyenPaiement;

    private String reference;

    private String banque;

    @Column(nullable = true)
    private LocalDate dateEcheance;

    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    private PaymentStatus statut;
}