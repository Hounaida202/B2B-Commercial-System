package com.example.demo.controllers;


import com.example.demo.DTOs.Requests.CommandeRequestDTO;
import com.example.demo.DTOs.Responses.CommandeResponseDTO;
import com.example.demo.services.CommandeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/commande")
public class CommandeController {
    @Autowired
    private CommandeService commandeService;

    @PostMapping("creerCommande")
    public ResponseEntity<CommandeResponseDTO> creerCommande(@RequestBody CommandeRequestDTO dto , HttpSession session){
        CommandeResponseDTO commandeResponseDTO =commandeService.creerCommande(dto ,session);

        return ResponseEntity.ok(commandeResponseDTO);
    }

    @PutMapping("/confirmerCommandes/{id}")
    public ResponseEntity<String> confirmerCommande(@PathVariable Long id) {

        commandeService.confirmerCommande(id);

        return ResponseEntity.status(200).body("succes");
    }

}
