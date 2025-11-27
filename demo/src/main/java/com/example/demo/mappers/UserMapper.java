package com.example.demo.mappers;

import com.example.demo.DTOs.Requests.UserDTO;
import com.example.demo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.lang.annotation.Target;

@Mapper(componentModel="spring")

public interface UserMapper {

    @Mapping(source="userRole" , target="role")

    User toEntity(UserDTO userDto);

    @Mapping(source="role" , target="userRole")
    UserDTO toDTO(User user);
}
