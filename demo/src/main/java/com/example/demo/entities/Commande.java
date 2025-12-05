package com.example.demo.entities;

import com.example.demo.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
@Data
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "commande" , cascade=CascadeType.ALL)
    private List<CommandeProduit> commande_produit = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    private LocalDate date;

    private Double sousTotal;

    private Double remise;

    private Double tva;

    private Double total;

    @Column(nullable = true)
    private String codePromo;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Double montantRestant;


}

