package com.peeptodo.peeptodo_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/api/test")
    public String testConnection() {
        String sql = "SELECT 'Success' FROM dual";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}