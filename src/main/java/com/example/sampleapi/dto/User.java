package com.example.sampleapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
        private String UserId;
        private String name;
        private String password;
        private LocalDate createdAt;
    }
