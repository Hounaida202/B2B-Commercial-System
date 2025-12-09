package com.example.demo.controllers;

import com.example.demo.DTOs.Requests.AuthRequestDTO;
import com.example.demo.DTOs.Responses.AuthResponseDTO;
import com.example.demo.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AuthController {

      @Autowired
      private AuthService authService;

      @PostMapping("login")
      public ResponseEntity<?> login( @RequestBody AuthRequestDTO userDTO,
                                                   HttpSession session){
          AuthResponseDTO logged = authService.login(userDTO,session);
          if(logged==null){
              return ResponseEntity
                      .status(400)
                      .body("infos incorrectes");
          }
          return ResponseEntity.ok(logged);

      }

}
