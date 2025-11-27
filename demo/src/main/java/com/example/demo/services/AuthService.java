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





}
