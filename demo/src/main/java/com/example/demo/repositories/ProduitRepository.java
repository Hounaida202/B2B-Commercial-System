package com.example.demo.repositories;

import com.example.demo.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Page<Produit> findByNomContainingIgnoreCaseAndStock( String nom ,Integer stock , Pageable page);
    Page<Produit> findByNomContainingIgnoreCase(String nom, Pageable pageable);

    Page<Produit> findByStock(Integer stock, Pageable pageable);

}
