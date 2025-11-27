package com.example.demo.DTOs.Requests;

import com.example.demo.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    @NotBlank(message="email est obligatoire")
    @Email(message = "format d'email invalide")
    private String username;

    @NotNull(message="role est obligatoire")
    private UserRole userRole;

    private String password;

}
