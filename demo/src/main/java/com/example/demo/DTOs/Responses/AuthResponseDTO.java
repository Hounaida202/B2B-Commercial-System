package com.example.demo.DTOs.Responses;

import com.example.demo.enums.CustomerTier;
import com.example.demo.enums.UserRole;
import lombok.Data;

@Data
public class AuthResponseDTO {
    private String username ;
    private UserRole role ;
    private String msg ;



}
