package com.example.demo.controllers;

import com.example.demo.DTOs.Requests.PaiementRequestDTO;
import com.example.demo.DTOs.Responses.PaiementResponseDTO;
import com.example.demo.services.PaiementService;
import com.example.demo.utils.RoleChecker;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/paiement")
public class PaiementController {


    @Autowired
    private PaiementService paiementService;


    @Autowired
    private RoleChecker roleChecker;


    @PostMapping("/commande/{commandeId}")
    public ResponseEntity<PaiementResponseDTO> ajouterPaiement(
            @PathVariable Long commandeId,
            @Valid @RequestBody PaiementRequestDTO dto,
            HttpSession session) {

        roleChecker.verifierAdmin(session);

        PaiementResponseDTO paiement = paiementService.ajouterPaiement(commandeId, dto);
        return ResponseEntity.ok(paiement);
    }


    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<PaiementResponseDTO>> getPaiementsCommande(
            @PathVariable Long commandeId,
            HttpSession session) {

        roleChecker.verifierConnexion(session);

        List<PaiementResponseDTO> paiements = paiementService.getPaiementsParCommande(commandeId);
        return ResponseEntity.ok(paiements);
    }


    @PutMapping("/encaisser/{paiementId}")
    public ResponseEntity<PaiementResponseDTO> encaisserCheque(
            @PathVariable Long paiementId,
            HttpSession session) {

        roleChecker.verifierAdmin(session);

        PaiementResponseDTO paiement = paiementService.encaisserCheque(paiementId);
        return ResponseEntity.ok(paiement);
    }


    @PutMapping("/rejeter/{paiementId}")
    public ResponseEntity<PaiementResponseDTO> rejeterPaiement(
            @PathVariable Long paiementId,
            HttpSession session) {

        roleChecker.verifierConnexion(session);

        roleChecker.verifierAdmin(session);

        PaiementResponseDTO paiement = paiementService.rejeterPaiement(paiementId);
        return ResponseEntity.ok(paiement);
    }


}