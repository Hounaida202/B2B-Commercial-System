package com.example.demo.DTOs.Responses;

import com.example.demo.DTOs.Requests.ProduitDTO;
import com.example.demo.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class CommandeResponseDTO {


    private List<CommandeProduitResponseDTO> produits;

    private LocalDate date;

    private Double sousTotal;

    private Double remise;

    private Double tva;

    private Double total;


    private OrderStatus orderStatus;

    private Double montantRestant;
}
