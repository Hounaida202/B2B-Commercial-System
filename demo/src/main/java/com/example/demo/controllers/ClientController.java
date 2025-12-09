
package com.example.demo.controllers;

import com.example.demo.DTOs.Requests.ClientDTO;
import com.example.demo.DTOs.Responses.ClientResponseDTO;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.services.ClientService;
import com.example.demo.utils.RoleChecker;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoleChecker roleChecker;


    @GetMapping("client/{id}")
    public ResponseEntity<ClientResponseDTO> getOneClient(
            @PathVariable Long id,
            HttpSession session) {

        roleChecker.verifierConnexion(session);

        if (roleChecker.estClient(session)) {
            Long clientId = roleChecker.getClientId(session);
            if (!id.equals(clientId)) {
                throw new ForbiddenException("Vous ne pouvez consulter que vos propres informations");
            }
        }

        ClientResponseDTO result = clientService.getOneClientInfos(id);
        return ResponseEntity.ok(result);
    }


    @PutMapping("updateClient/{id}")
    public ResponseEntity<String> updateClient(
            @PathVariable Long id,
            @RequestBody ClientDTO client,
            HttpSession session) {

        roleChecker.verifierAdmin(session);

        boolean updated = clientService.updateClient(client, id);
        if (updated) {
            return ResponseEntity.ok("Client modifié avec succès");
        }
        return ResponseEntity.status(404).body("Ce client n'existe pas");
    }


    @DeleteMapping("deleteClient/{id}")
    public ResponseEntity<String> deleteClient(
            @PathVariable Long id,
            HttpSession session) {

        roleChecker.verifierAdmin(session);

        boolean deleted = clientService.deleteClient(id);
        if (deleted) {
            return ResponseEntity.ok("Client supprimé avec succès");
        }
        return ResponseEntity.status(404).body("Ce client n'existe pas");
    }


    @PostMapping("creerClient")
    public ResponseEntity<ClientResponseDTO> creerClient(
            @Valid @RequestBody ClientDTO clientdto,
            HttpSession session) {

        roleChecker.verifierAdmin(session);

        ClientResponseDTO saved = clientService.creerrClient(clientdto);
        return ResponseEntity.ok(saved);
    }
}
