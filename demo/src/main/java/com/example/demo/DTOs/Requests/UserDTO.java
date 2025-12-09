package com.example.demo.DTOs.Requests;

import com.example.demo.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    @NotBlank(message="email est obligatoire")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "email doit respecter le format standard : exemple@gmail.com"
    )
    private String username;

    @Column(nullable = true)
    private UserRole userRole;

//    private String password;

}