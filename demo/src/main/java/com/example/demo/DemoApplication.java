package com.example.demo;

import com.example.demo.entities.Client;
import com.example.demo.entities.User;
import com.example.demo.seeds.AdminSeeder;
import com.example.demo.seeds.ClientSeeder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {



    public static void main(String[] args) {
        var context = SpringApplication.run(DemoApplication.class, args);

        AdminSeeder admin = context.getBean(AdminSeeder.class);
        ClientSeeder client = context.getBean(ClientSeeder.class);


//        User adminUser = admin.loadAdmin();
//        Client clientUser = client.loadClient();
//
//        client.recupererclients();



    }

}
