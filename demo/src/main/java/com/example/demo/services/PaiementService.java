package com.example.demo.services;

import com.example.demo.DTOs.Requests.PaiementRequestDTO;
import com.example.demo.DTOs.Responses.PaiementResponseDTO;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Paiement;
import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.UnprocessableEntityException;
import com.example.demo.mappers.PaiementMapper;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.repositories.PaiementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private PaiementMapper paiementMapper;






    @Transactional
    public PaiementResponseDTO ajouterPaiement(Long commandeId, PaiementRequestDTO dto) {

        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new NotFoundException("Commande introuvable"));

        if (commande.getMontantRestant() == null || commande.getMontantRestant() == 0) {
            throw new UnprocessableEntityException("Cette commande est déjà totalement payée");
        }
        if (dto.getMontant() > commande.getMontantRestant()) {
            throw new UnprocessableEntityException("Le montant du paiement dépasse le montant restant ");
        }

        if (dto.getMoyenPaiement() == PaymentMethod.ESPECES) {
            if (dto.getMontant() > 20000) {
                throw new UnprocessableEntityException("Le paiement en espèces ne peut pas dépasser 20 000 DH");
            }
        }

        if (dto.getMoyenPaiement() == PaymentMethod.ESPECES
                || dto.getMoyenPaiement() == PaymentMethod.VIREMENT) {

            if (!commande.getMontantRestant().equals(dto.getMontant())) {
                throw new BadRequestException(
                        "Le montant du paiement doit être exactement égal au montant restant de la commande"
                );
            }
        }


        if (dto.getMoyenPaiement() == PaymentMethod.CHEQUE) {
            if (dto.getBanque() == null || dto.getBanque().trim().isEmpty()) {
                throw new BadRequestException("La banque est obligatoire pour un paiement par chèque");
            }
            if (dto.getDateEcheance() == null) {
                throw new BadRequestException("La date d'échéance est obligatoire pour un paiement par chèque");
            }
        }

        if (dto.getMoyenPaiement() == PaymentMethod.VIREMENT) {
            if (dto.getBanque() == null || dto.getBanque().trim().isEmpty()) {
                throw new BadRequestException("La banque est obligatoire pour un paiement par virement");
            }
        }

        Paiement paiement = paiementMapper.toEntity(dto);
        paiement.setCommande(commande);
        paiement.setDatePaiement(LocalDate.now());

        if (dto.getMoyenPaiement() == PaymentMethod.ESPECES) {
            paiement.setStatut(PaymentStatus.ENCAISSE);
        } else if (dto.getMoyenPaiement() == PaymentMethod.VIREMENT) {
            paiement.setStatut(PaymentStatus.ENCAISSE);
        } else if (dto.getMoyenPaiement() == PaymentMethod.CHEQUE) {
            paiement.setStatut(PaymentStatus.EN_ATTENTE);
        }

        long nombreCheques = paiementRepository.countByCommandeIdAndMoyenPaiement(
                commandeId,
                PaymentMethod.CHEQUE
        );

        if (dto.getMoyenPaiement() == PaymentMethod.CHEQUE) {

            if (nombreCheques == 2) {

                double montantRestantApresPaiement = commande.getMontantRestant() - dto.getMontant();

                if (montantRestantApresPaiement > 0) {
                    throw new BadRequestException(
                            "Le montant total des 3 chèques n'est pas suffisant pour régler la commande"
                    );
                }
            }
            if (nombreCheques >= 3) {
                throw new BadRequestException("Vous ne pouvez pas dépasser 3 paiements par chèque");
            }
        }
        paiementRepository.save(paiement);

        double nouveauMontantRestant = commande.getMontantRestant() - dto.getMontant();
        commande.setMontantRestant(nouveauMontantRestant);
        commandeRepository.save(commande);

        return paiementMapper.toDTO(paiement);
    }














    public List<PaiementResponseDTO> getPaiementsParCommande(Long commandeId) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new NotFoundException("Commande introuvable"));

        List<Paiement> paiements = paiementRepository.findByCommandeId(commandeId);
        return paiements.stream()
                .map(paiementMapper::toDTO)
                .collect(Collectors.toList());
    }




//    public List<Paiement> getPaiementTest(Long commandeId) {
//        Commande commande = commandeRepository.findById(commandeId)
//                .orElseThrow(() -> new NotFoundException("Commande introuvable"));
//
//        List<Paiement> paiements = paiementRepository.findByCommandeId(commandeId);
//
//        Optional<Paiement> ps = paiements.stream().
//                            filter(p->p.getStatut().equals("ENCAISSE"))
//                .collect(Collectors.minBy(Comparator.comparing(Paiement::getMontant)));
//
//        return ps;
//    }



    @Transactional
    public PaiementResponseDTO encaisserCheque(Long paiementId) {
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new NotFoundException("Paiement introuvable"));

        if (paiement.getMoyenPaiement() != PaymentMethod.CHEQUE) {
            throw new BadRequestException("Seuls les chèques peuvent être encaissés");
        }

        if (paiement.getStatut() == PaymentStatus.ENCAISSE) {
            throw new BadRequestException("Ce chèque est déjà encaissé");
        }

        if (paiement.getStatut() == PaymentStatus.REJETE) {
            throw new BadRequestException("Ce chèque a été rejeté");
        }

        paiement.setStatut(PaymentStatus.ENCAISSE);
        paiementRepository.save(paiement);

        return paiementMapper.toDTO(paiement);
    }


    @Transactional
    public PaiementResponseDTO rejeterPaiement(Long paiementId) {
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new NotFoundException("Paiement introuvable"));

        if (paiement.getMoyenPaiement() == PaymentMethod.ESPECES) {
            throw new BadRequestException("Un paiement en espèces ne peut pas être rejeté");
        }

        if (paiement.getStatut() == PaymentStatus.REJETE) {
            throw new BadRequestException("Ce paiement est déjà rejeté");
        }

        paiement.setStatut(PaymentStatus.REJETE);
        paiementRepository.save(paiement);

//        Commande commande = paiement.getCommande();
//        double nouveauMontantRestant = commande.getMontantRestant() + paiement.getMontant();
//        commande.setMontantRestant(nouveauMontantRestant);
//        commandeRepository.save(commande);

        return paiementMapper.toDTO(paiement);
    }
}