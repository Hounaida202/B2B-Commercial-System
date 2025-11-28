package com.example.demo.entities;

import com.example.demo.enums.CustomerTier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Enumerated(EnumType.STRING)
    private CustomerTier customerTier;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user ;


    @OneToMany(mappedBy = "client")
    private List<Commande> commandes;

}
