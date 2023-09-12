package com.peeptodo.peeptodo_backend.config;

import lombok.Getter;

@Getter
public enum DomainUrl {
//    // local test
//    BACKEND("http://localhost:8080"),
//    FRONTEND("http://localhost:3000");

    // infra
    BACKEND("https://peeptodo.com"),
    FRONTEND("https://peeptodo.com");

    private final String value;

    DomainUrl(String value){
        this.value = value;
    }
}
