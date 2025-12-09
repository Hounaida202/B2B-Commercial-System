package com.example.demo.services;

import com.example.demo.DTOs.Requests.ClientDTO;
import com.example.demo.DTOs.Responses.ClientResponseDTO;
import com.example.demo.DTOs.Responses.UserResponseDTO;
import com.example.demo.entities.Client;
import com.example.demo.entities.User;
import com.example.demo.enums.CustomerTier;
import com.example.demo.enums.UserRole;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.ClientMapper;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


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

//    public ClientDTO creerClient(ClientDTO clientdto){
//
//        clientdto.getUser().setPassword(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
//
//        Client client = clientMapper.toEntity(clientdto);
//        client.setUser();
//        client.setCustomerTier(CustomerTier.BASIC);
//
//        Client saved=clientRepository.save(client);
//
//        return clientMapper.toDTO(saved);
//
//    }


    public ClientResponseDTO creerrClient(ClientDTO dto){


        User user = new User();
        user.setRole(UserRole.CLIENT);

        if(userRepository.countByUsername(dto.getUser().getUsername())>0){
            throw new IllegalArgumentException("username existe deja");

        }
        else{
            user.setUsername(dto.getUser().getUsername());


            String password = UUID.randomUUID().toString().replace("-", "").substring(0, 10);


            String crypted = passwordEncoder.encode(password);
            user.setPassword(crypted);

            Client client = clientMapper.toEntity(dto);

            client.setUser(user);
            client.setCustomerTier(CustomerTier.BASIC);


            Client saved=clientRepository.save(client);
            ClientResponseDTO reponse = clientMapper.toDTO(saved);

            UserResponseDTO userresponse = reponse.getUser();
            userresponse.setPassword(password);
            reponse.setUser(userresponse);

            return reponse;
        }


    }





}
