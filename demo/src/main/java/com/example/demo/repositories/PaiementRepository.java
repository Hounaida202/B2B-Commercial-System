package com.example.demo.repositories;

import com.example.demo.entities.Paiement;
import com.example.demo.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByCommandeId(Long commandeId);
    long countByCommandeIdAndMoyenPaiement(Long commandeId, PaymentMethod moyenPaiement);

}