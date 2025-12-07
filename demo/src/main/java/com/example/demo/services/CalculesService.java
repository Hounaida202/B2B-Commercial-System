package com.example.demo.services;

import com.example.demo.entities.Client;
import com.example.demo.entities.Commande;
import com.example.demo.entities.CommandeProduit;
import com.example.demo.enums.CustomerTier;
import org.springframework.stereotype.Service;

@Service
public class CalculesService {




public Double calculerRemise(Client client, Commande commande) {
    double remisePourcentage = 0.0;
    if (client.getCustomerTier() == null || client.getCustomerTier() == CustomerTier.BASIC) {
        remisePourcentage = 0.0;
    } else if (client.getCustomerTier() == CustomerTier.SILVER) {
        remisePourcentage = 5.0;
    } else if (client.getCustomerTier() == CustomerTier.GOLD) {
        remisePourcentage = 10.0;
    } else if (client.getCustomerTier() == CustomerTier.PLATINUM) {
        remisePourcentage = 15.0;
    }

    if (commande.getCodePromo() != null) {
        remisePourcentage += 5.0;
    }

    double totalCommande = commande.getTotal() != null ? commande.getTotal() : 0.0;
    double remise = totalCommande * remisePourcentage / 100.0;

    return remise;
}



    public Double calculerSousTotal(Client client, Commande commande) {
        Double remise = calculerRemise(client, commande);
        double totalCommande = commande.getTotal();
        double sousTotal = totalCommande - remise;
        return sousTotal;
    }



    public Double calculerTotal(Commande commande) {
        if (commande.getCommande_produit() == null ) {
            return 0.0;
        }
        return commande.getCommande_produit()
                .stream()
                .mapToDouble(CommandeProduit::getTotalLigne)
                .sum();
    }

    public Double calculerTVA(Double total){
        Double x = 20 * total /100;
        Double tva = total + x;
    return tva;
    }



}
