package com.example.demo.services;

import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.DTOs.Responses.ProduitResponseDTO;
import com.example.demo.entities.Produit;
import com.example.demo.mappers.ProduitMapper;
import com.example.demo.repositories.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private ProduitMapper produitMapper;

    @InjectMocks
    private ProduitService produitService;

    private ProduitDTO produitDTO;
    private Produit produitEntity;
    private ProduitResponseDTO produitResponseDTO;


    @BeforeEach
    void setUp() {
        produitDTO = new ProduitDTO();
        produitDTO.setNom("PC Portable HP");
        produitDTO.setPrix_unitaire(8500.0);
        produitDTO.setStock(15);

        produitEntity = new Produit();
        produitEntity.setId(1L);
        produitEntity.setNom("PC Portable HP");
        produitEntity.setPrix_unitaire(8500.0);
        produitEntity.setStock(15);

        produitResponseDTO = new ProduitResponseDTO();
        produitResponseDTO.setNom("PC Portable HP");
        produitResponseDTO.setPrix_unitaire(8500.0);
        produitResponseDTO.setStock(15);
    }


    @Test
    void testCreerProduit_Success() {

        when(produitMapper.toEntity(produitDTO)).thenReturn(produitEntity);
        when(produitRepository.save(any(Produit.class))).thenReturn(produitEntity);
        when(produitMapper.toDTO(produitEntity)).thenReturn(produitResponseDTO);

        ProduitResponseDTO result = produitService.creerProduit(produitDTO);


        assertNotNull(result, "Le résultat ne doit pas être null");

        assertEquals("PC Portable HP", result.getNom(), "Le nom doit être 'PC Portable HP'");
        assertEquals(8500.0, result.getPrix_unitaire(), "Le prix doit être 8500.0");
        assertEquals(15, result.getStock(), "Le stock doit être 15");

        verify(produitMapper, times(1)).toEntity(produitDTO);
        verify(produitRepository, times(1)).save(produitEntity);
        verify(produitMapper, times(1)).toDTO(produitEntity);
    }


    /*@Test
    void testCreerProduit_AvecAutresProduit() {
        ProduitDTO sourisDTO = new ProduitDTO();
        sourisDTO.setNom("Souris Logitech");
        sourisDTO.setPrix_unitaire(150.0);
        sourisDTO.setStock(50);

        Produit sourisEntity = new Produit();
        sourisEntity.setId(2L);
        sourisEntity.setNom("Souris Logitech");
        sourisEntity.setPrix_unitaire(150.0);
        sourisEntity.setStock(50);

        ProduitResponseDTO sourisResponse = new ProduitResponseDTO();
        sourisResponse.setNom("Souris Logitech");
        sourisResponse.setPrix_unitaire(150.0);
        sourisResponse.setStock(50);

        when(produitMapper.toEntity(sourisDTO)).thenReturn(sourisEntity);
        when(produitRepository.save(any(Produit.class))).thenReturn(sourisEntity);
        when(produitMapper.toDTO(sourisEntity)).thenReturn(sourisResponse);

        ProduitResponseDTO result = produitService.creerProduit(sourisDTO);

        assertNotNull(result);
        assertEquals("Souris Logitech", result.getNom());
        assertEquals(150.0, result.getPrix_unitaire());
        assertEquals(50, result.getStock());

        verify(produitMapper, times(1)).toEntity(sourisDTO);
        verify(produitRepository, times(1)).save(sourisEntity);
        verify(produitMapper, times(1)).toDTO(sourisEntity);
    }*/
}