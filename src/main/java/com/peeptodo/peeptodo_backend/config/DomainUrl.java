package com.peeptodo.peeptodo_backend.config;

import lombok.Getter;

@Getter
public enum DomainUrl {
//    // local test
//    FRONTEND("http://localhost:3000"),
    // infra
    FRONTEND("https://peeptodo.com"),
    // 백엔드 로컬 http://localhost:8100
    BACKEND("http://localhost:8100");
    
    private final String value;

    DomainUrl(String value) {
        this.value = value;
    }
}
