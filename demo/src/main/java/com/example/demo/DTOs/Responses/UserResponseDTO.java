package com.example.demo.DTOs.Responses;

import com.example.demo.enums.UserRole;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String username ;
    private UserRole role ;
}
