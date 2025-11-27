package com.example.demo.mappers;


import com.example.demo.DTOs.Requests.AuthRequestDTO;
import com.example.demo.entities.User;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel="spring")
public interface AuthMapper {




//    from dto to entity

    User toEntity(AuthRequestDTO userDTO);

//    from dto to entity

    AuthRequestDTO toDTO(User user);
}
