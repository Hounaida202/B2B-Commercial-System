package com.example.demo.DTOs.Requests;

import lombok.Data;

@Data
public class OrderItemRequestDTO {
    private Long produitId;
    private int quantite;
}
