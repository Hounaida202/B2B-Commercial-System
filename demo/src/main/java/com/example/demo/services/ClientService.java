package com.example.demo.services;

import com.example.demo.DTOs.Requests.ClientDTO;
import com.example.demo.DTOs.Requests.ClientDTO;
import com.example.demo.DTOs.Responses.ClientResponseDTO;
import com.example.demo.DTOs.Responses.UserResponseDTO;
import com.example.demo.entities.Client;
import com.example.demo.entities.User;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.AuthMapper;
import com.example.demo.mappers.ClientMapper;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class ClientService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private ClientRepository clientRepository;


    public ClientResponseDTO getOneClientInfos(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client introuvable"));

        ClientResponseDTO clientDTO= new ClientResponseDTO();
        clientDTO.setNom(client.getNom());
        clientDTO.setCustomerTier(client.getCustomerTier());

        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setUsername(client.getUser().getUsername());
        userDTO.setRole(client.getUser().getRole());

        clientDTO.setUser(userDTO);

        return clientDTO;
    }


    public boolean updateClient(ClientDTO clientUpdateDto , Long id){
        boolean existe = clientRepository.existsById(id);
        if(existe){
            Optional<Client> clientopt = clientRepository.findById(id);
            Client client = clientopt.orElseThrow(()->new RuntimeException("client n existe pas"));

            client.setNom(clientUpdateDto.getNom());
            client.setCustomerTier(clientUpdateDto.getCustomerTier());

            User user = client.getUser();
            user.setUsername(clientUpdateDto.getUser().getUsername());
            user.setRole(clientUpdateDto.getUser().getUserRole());

            client.setUser(user);

            clientRepository.save(client);
            return true ;
        }
        else
            return false;
    }




    public boolean deleteClient(Long id){
        boolean existe = clientRepository.existsById(id);
        if(existe){
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ClientDTO creerClient(ClientDTO clientdto){

        clientdto.getUser().setPassword(UUID.randomUUID().toString().replace("-", "").substring(0, 10));

        Client client = clientMapper.toEntity(clientdto);

        Client saved=clientRepository.save(client);

        return clientMapper.toDTO(saved);

    }
}
