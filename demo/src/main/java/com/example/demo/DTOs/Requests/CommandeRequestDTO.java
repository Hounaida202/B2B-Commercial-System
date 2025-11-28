package com.example.demo.DTOs.Requests;

import com.example.demo.enums.OrderStatus;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CommandeRequestDTO {

    private List<Long> produitids;

    private Long clientId;

    private LocalDate date;

    private Double sousTotal;

    private Double remise;

    private Double tva;

    private Double total;

    private String codePromo;

    private OrderStatus orderStatus;

    private Double montantRestant;
}
