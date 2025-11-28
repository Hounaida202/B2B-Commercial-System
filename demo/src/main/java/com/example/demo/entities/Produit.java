package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double prix_unitaire;

    private int stock;
    private String nom;

    @OneToMany(mappedBy = "produit")
    private List<CommandeProduit> commande_produit;
}
