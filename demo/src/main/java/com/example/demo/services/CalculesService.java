
package com.example.demo.services;

import com.example.demo.entities.Client;
import com.example.demo.entities.Commande;
import com.example.demo.entities.CommandeProduit;
import com.example.demo.enums.CustomerTier;
import org.springframework.stereotype.Service;

@Service
public class CalculesService {


    public Double calculerSousTotal(Commande commande) {
        if (commande.getCommande_produit() == null || commande.getCommande_produit().isEmpty()) {
            return 0.0;
        }
        return commande.getCommande_produit()
                .stream()
                .mapToDouble(CommandeProduit::getTotalLigne)
                .sum();
    }


    public Double calculerRemise(Client client, Commande commande) {
        double sousTotal = calculerSousTotal(commande);
        double remisePourcentage = 0.0;

        CustomerTier tier = client.getCustomerTier();

        if (tier == CustomerTier.SILVER && sousTotal >= 500) {
            remisePourcentage = 5.0;
        } else if (tier == CustomerTier.GOLD && sousTotal >= 800) {
            remisePourcentage = 10.0;
        } else if (tier == CustomerTier.PLATINUM && sousTotal >= 1200) {
            remisePourcentage = 15.0;
        }

        if (commande.getCodePromo() != null && !commande.getCodePromo().trim().isEmpty()) {
            remisePourcentage += 5.0;
        }

        double montantRemise = sousTotal * remisePourcentage / 100.0;

        return montantRemise;
    }


    public Double calculerMontantHTApresRemise(Commande commande, Double remise) {
        double sousTotal = calculerSousTotal(commande);
        return sousTotal - remise;
    }


    public Double calculerTVA(Double montantHTApresRemise) {
        return montantHTApresRemise * 0.20;
    }


    public Double calculerTotalTTC(Double montantHTApresRemise, Double tva) {
        return montantHTApresRemise + tva;
    }
}
