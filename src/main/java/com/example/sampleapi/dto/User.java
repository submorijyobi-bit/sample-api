package com.example.sampleapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
        private String userId;
        private String username;
        private String password;
        private LocalDate createdAt;
    }
