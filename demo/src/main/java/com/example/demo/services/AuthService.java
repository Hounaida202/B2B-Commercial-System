package com.example.demo.services;

import com.example.demo.DTOs.Requests.AuthRequestDTO;

import com.example.demo.DTOs.Responses.AuthResponseDTO;

import com.example.demo.entities.User;

import com.example.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public AuthResponseDTO login(AuthRequestDTO userDTO, HttpSession session){

              User z = userRepository.findByUsername(userDTO.getUsername());
              if(z==null){
                  return null;
              }
              else{
                  if(z.getPassword().equals(userDTO.getPassword())){
                      session.setAttribute("userID",z.getId());
                      session.setAttribute("username",z.getUsername());
                      session.setAttribute("role",z.getRole());

                      AuthResponseDTO logged = new AuthResponseDTO();
                      logged.setUsername(z.getUsername());
                      logged.setRole(z.getRole());
                      logged.setMsg("connexion reussite et nouvelle session crée");
                      return logged;
                  }
                  return null;
              }
          }



}
