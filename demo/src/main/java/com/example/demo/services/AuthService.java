package com.example.demo.services;

import com.example.demo.DTOs.Requests.AuthRequestDTO;

import com.example.demo.DTOs.Responses.AuthResponseDTO;

import com.example.demo.entities.Client;
import com.example.demo.entities.User;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public AuthResponseDTO login(AuthRequestDTO userDTO, HttpSession session) {

        User z = userRepository.findByUsername(userDTO.getUsername());
        if (z == null) {
            throw new NotFoundException("User non trouvé");
        }

//        if (!z.getPassword().equals(userDTO.getPassword())) {
//            throw new NotFoundException("mot de passe incorrecte");
//        }
        // Changer cette ligne
        if (!passwordEncoder.matches(userDTO.getPassword(), z.getPassword())) {
            throw new NotFoundException("mot de passe incorrecte");
        }

        session.setAttribute("userID", z.getId());
        session.setAttribute("username", z.getUsername());
        session.setAttribute("role", z.getRole());

        Client client = clientRepository.findByUserId(z.getId());

        if (client != null) {
            session.setAttribute("clientID", client.getId());
        }

        AuthResponseDTO logged = new AuthResponseDTO();
        logged.setUsername(z.getUsername());
        logged.setRole(z.getRole());
        logged.setMsg("connexion reussite");

        return logged;
    }



}
