package com.peeptodo.peeptodo_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // 스프링 시큐리티 일단 꺼두었음, 나중에 추가
public class PeeptodoBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeeptodoBackendApplication.class, args);
    }

}
