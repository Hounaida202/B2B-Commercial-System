package com.example.demo.controllers;

import com.example.demo.DTOs.Requests.CommandeRequestDTO;
import com.example.demo.DTOs.Responses.CommandeResponseDTO;
import com.example.demo.services.CommandeService;
import com.example.demo.utils.RoleChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/commande")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private RoleChecker roleChecker;


    @PostMapping("creerCommande")
    public ResponseEntity<CommandeResponseDTO> creerCommande(
            @RequestBody CommandeRequestDTO dto,
            HttpSession session) {

        roleChecker.verifierClient(session);

        CommandeResponseDTO commande = commandeService.creerCommande(dto, session);
        return ResponseEntity.ok(commande);
    }


    @PutMapping("/confirmerCommandes/{id}")
    public ResponseEntity<CommandeResponseDTO> confirmerCommande(
            @PathVariable Long id,
            HttpSession session) {

        roleChecker.verifierAdmin(session);

        CommandeResponseDTO commande = commandeService.confirmerCommande(id);
        return ResponseEntity.ok(commande);
    }
}
