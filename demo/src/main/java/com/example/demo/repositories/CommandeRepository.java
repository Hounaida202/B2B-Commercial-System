package com.example.demo.repositories;

import com.example.demo.entities.Commande;
import com.example.demo.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
    int countByClientIdAndOrderStatus(Long clientId, OrderStatus status);
    List<Commande> findByClientIdAndOrderStatus(Long id , OrderStatus orderStatus);

}
