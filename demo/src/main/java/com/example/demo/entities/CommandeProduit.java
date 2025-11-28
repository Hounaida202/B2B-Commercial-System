package com.example.demo.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="commande_produit")
@Data
public class CommandeProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="commande_id")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name="produit_id")
    private Produit produit;

}
