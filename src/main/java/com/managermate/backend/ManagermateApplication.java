package com.managermate.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ManagermateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagermateApplication.class, args);
    }

}
