package com.example.demo.controllers;


import com.example.demo.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/commande")
public class CommandeController {
    @Autowired
    private CommandeService commandeService;
}
