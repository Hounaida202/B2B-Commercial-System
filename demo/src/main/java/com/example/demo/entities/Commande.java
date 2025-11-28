package com.example.demo.entities;

import com.example.demo.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "commandes")
@Data
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "commande")
    private List<CommandeProduit> commande_produit;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    private LocalDate date;

    private Double sousTotal;

    private Double remise;

    private Double tva;

    private Double total;

    private String codePromo;

    private OrderStatus orderStatus;

    private Double montantRestant;
}

