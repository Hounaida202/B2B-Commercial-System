package com.example.demo.seeds;

import com.example.demo.entities.Client;
import com.example.demo.entities.User;
import com.example.demo.enums.CustomerTier;
import com.example.demo.enums.UserRole;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ClientSeeder {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional // Ajoutez cette annotation
    public Client loadClient(){
        // Vérifiez si un client existe déjà
        if(clientRepository.count() == 0) {
            User user = User.builder()
                    .username("1@gmail.com")
                    .password("123456")
                    .role(UserRole.CLIENT)
                    .build();
            userRepository.save(user);
            Client client1 = Client.builder()
                    .nom("client")
                    .customerTier(CustomerTier.BASIC)
                    .user(user)
                    .build();
            return clientRepository.save(client1);
        }
        return null;
    }

    public void recupererclients(){
        List<Client> clients = clientRepository.findAll();
        for(Client f : clients){
            System.out.println("nom est" + f.getNom());
            System.out.println("nom est" + f.getUser().getUsername() );
        }

    }


}