package com.example.demo.services;


import com.example.demo.repositories.CommandeProduitRepository;
import com.example.demo.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeProduitRepository commandeProduitRepository;

}
