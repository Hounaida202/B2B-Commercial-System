package com.example.demo.controllers;

import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.DTOs.Responses.ProduitResponseDTO;
import com.example.demo.services.ProduitService;
import com.example.demo.utils.RoleChecker;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/produit")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private RoleChecker roleChecker;


    @PostMapping("creerProduit")
    public ResponseEntity<ProduitDTO> creerProduit(
            @Valid @RequestBody ProduitDTO produitDto,
            HttpSession session) {

        roleChecker.verifierAdmin(session);

        produitService.creerProduit(produitDto);
        return ResponseEntity.ok(produitDto);
    }


    @PutMapping("modifierProduit/{id}")
    public ResponseEntity<ProduitResponseDTO> modifierProduit(
            @Valid @RequestBody ProduitDTO produitdto,
            @PathVariable Long id,
            HttpSession session) {

        roleChecker.verifierAdmin(session);

        ProduitResponseDTO updated = produitService.modifierProduit(id, produitdto);
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/produits")
    public ResponseEntity<List<ProduitResponseDTO>> getProduits(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Integer stock,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            HttpSession session) {

        roleChecker.verifierConnexion(session);

        Pageable pageable = PageRequest.of(page, size);
        Page<ProduitResponseDTO> pageResult = produitService
                .recuperProduitsAvecPaginationEtFilter(nom, stock, pageable);

        return ResponseEntity.ok(pageResult.getContent());
    }
}