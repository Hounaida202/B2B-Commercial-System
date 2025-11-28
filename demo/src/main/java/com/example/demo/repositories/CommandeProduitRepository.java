package com.example.demo.repositories;

import com.example.demo.entities.CommandeProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeProduitRepository extends JpaRepository<CommandeProduit,Long> {
}
