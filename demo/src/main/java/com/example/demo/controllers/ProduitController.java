package com.example.demo.controllers;

import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.entities.Produit;
import com.example.demo.services.ProduitService;
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

    @PostMapping("creerProduit")
    public ResponseEntity<?> creerProduit(@Valid @RequestBody ProduitDTO produitDto) {
        produitService.creerProduit(produitDto);
        return ResponseEntity.ok(produitDto);
    }

    @PutMapping("modifierProduit/{id}")
    public ResponseEntity<?> modifierProduit(@Valid @RequestBody ProduitDTO produitdto,
                                             @PathVariable Long id) {

        ProduitDTO updated = produitService.modifierProduit(id, produitdto);

        return ResponseEntity.ok(updated);
    }


    @GetMapping("/produits")
    public ResponseEntity<List<ProduitDTO>> getProduits(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Integer stock,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProduitDTO> pageResult = produitService.recuperProduitsAvecPaginationEtFilter(
                nom,
                stock,
                pageable
        );

        return ResponseEntity.ok(pageResult.getContent());
    }
}