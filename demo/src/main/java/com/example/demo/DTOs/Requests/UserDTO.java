package com.example.demo.DTOs.Requests;

import com.example.demo.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDTO {

    private String username;
    private UserRole userRole;

}
