package com.peeptodo.peeptodo_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeeptodoBackendApplication {
    public static void main(String[] args) {
        System.out.println("DB_PASSWORD : "+System.getenv("DB_PASSWORD"));
        System.out.println("DB_USERNAME : ADMIN");
        System.out.println("OAUTH_GOOGLE_ID : "+System.getenv("OAUTH_GOOGLE_ID"));
        System.out.println("OAUTH_GOOGLE_SECRET : "+System.getenv("OAUTH_GOOGLE_SECRET"));

        SpringApplication.run(PeeptodoBackendApplication.class, args);
    }
}
