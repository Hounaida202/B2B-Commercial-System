package com.example.demo.seeds;


import com.example.demo.entities.User;
import com.example.demo.enums.UserRole;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AdminSeeder {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    public User loadAdmin(){
            String password= passwordEncoder.encode("admin");
            User adminn =   User.builder()
                    .username("admin@gmail.com")
                    .password(password)
                    .role(UserRole.ADMIN).build();
            userRepository.save(adminn);
            return adminn;


    }


}
