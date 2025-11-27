package com.example.demo.controllers;

import com.example.demo.DTOs.Requests.ClientDTO;
import com.example.demo.DTOs.Responses.ClientResponseDTO;
import com.example.demo.services.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

//    @GetMapping("client/{id}")
//    public ResponseEntity<?> getOneClient(@PathVariable @NotNull(message="ne doit pas etre null") Long id){
//
//        ClientResponseDTO result = clientService.getOneClientInfos(id);
//        if(id!=null){
//            if(result!=null){
//                return ResponseEntity.ok(result);
//            }
//            return ResponseEntity.status(404).body("client inexiste");
//        }
//        return ResponseEntity.status(404).body("id ne doit pas etre nul");
//
//
//    }
@GetMapping("client/{id}")
public ResponseEntity<ClientResponseDTO> getOneClient(@PathVariable Long id) {
    ClientResponseDTO result = clientService.getOneClientInfos(id);
    return ResponseEntity.ok(result);
}



    @PutMapping("updateClient/{id}")
    public ResponseEntity<?> updateClient(
            @PathVariable Long id,
            @RequestBody ClientDTO client){
        boolean updated = clientService.updateClient(client ,id);
        if(updated){
            return ResponseEntity.status(200).body("updated with succes");
        }
        else
            return ResponseEntity.status(404).body("ce client n existe pas ");
    }



    @DeleteMapping("deleteClient/{id}")
    public ResponseEntity<?> deleteClient(
            @PathVariable Long id){
        boolean updated = clientService.deleteClient(id);
        if(updated){
            return ResponseEntity.status(200).body("deleted with succes");
        }
        else
            return ResponseEntity.status(404).body("ce client n existe pas ");
    }

    @PostMapping("creerClient")
    public ResponseEntity<?> creerClient(@Valid @RequestBody ClientDTO clientdto){

    ClientDTO saved =clientService.creerClient(clientdto);
    return ResponseEntity.ok(saved);

    }

}
