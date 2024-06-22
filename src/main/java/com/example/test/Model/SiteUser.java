package com.example.test.Model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SiteUser {
    private Long id;
    private String loginId;
    private String password;
    private Timestamp createdAt;
}