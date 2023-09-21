package com.peeptodo.peeptodo_backend.config;

import lombok.Getter;

@Getter
public enum DomainUrl {

    BACKEND("https://peeptodo.com"),
    FRONTEND("https://peeptodo.com");

//    BACKEND("http://localhost:8100"),
//    FRONTEND("http://localhost:3000");

    private final String value;

    DomainUrl(String value){
        this.value = value;
    }
}
