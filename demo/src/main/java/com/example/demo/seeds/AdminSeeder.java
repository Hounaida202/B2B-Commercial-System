package com.example.demo.seeds;


import com.example.demo.entities.User;
import com.example.demo.enums.UserRole;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AdminSeeder {

    @Autowired
    private UserRepository userRepository;


    public User loadAdmin(){

        if(userRepository.count()==0){
            User admin1 =   User.builder()
                    .username("test1")
                    .password("test")
                    .role(UserRole.ADMIN).build();
            userRepository.save(admin1);
            return admin1;
        }

         return null;

    }


}
