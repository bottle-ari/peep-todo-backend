package com.peeptodo.peeptodo_backend.config;

import lombok.Getter;

@Getter
public enum DomainUrl {
//    // local test
//    FRONTEND("http://localhost:3000"),
    // infra
    FRONTEND("https://peeptodo.com"),
    // 백엔드 로컬 http://localhost:8100
    BACKEND("https://peeptodo.com"); // TODO: 11/9/2023 여기서 8100을 application.properties에서 바로 가져오도록 수정
    
    private final String value;

    DomainUrl(String value){
        this.value = value;
    }
}
