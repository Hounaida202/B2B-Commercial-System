package com.example.demo.controllers;

import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.services.ProduitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/produit")
public class ProduitController {


    @Autowired
    private ProduitService produitService;

    @PostMapping("creerProduit")
    public ResponseEntity<?> creerProduit( @Valid @RequestBody ProduitDTO produitDto){
        produitService.creerProduit(produitDto);
        return ResponseEntity.ok(produitDto);
    }

    @PutMapping("modifierProduit/{id}")
    public ResponseEntity<?> modifierProduit(@Valid @RequestBody ProduitDTO produitdto ,
                                             @PathVariable Long id){

        ProduitDTO updated =produitService.modifierProduit(id,produitdto);

        return ResponseEntity.ok(updated);
    }
}
