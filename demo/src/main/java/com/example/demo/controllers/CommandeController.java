package com.example.demo.controllers;


import com.example.demo.DTOs.Requests.CommandeRequestDTO;
import com.example.demo.DTOs.Responses.CommandeResponseDTO;
import com.example.demo.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/commande")
public class CommandeController {
    @Autowired
    private CommandeService commandeService;

    @PostMapping("creerCommande")
    public ResponseEntity<CommandeResponseDTO> creerCommande(@RequestBody CommandeRequestDTO dto){
        CommandeResponseDTO commandeResponseDTO =commandeService.creerCommande(dto);

        return ResponseEntity.ok(commandeResponseDTO);
    }


}
