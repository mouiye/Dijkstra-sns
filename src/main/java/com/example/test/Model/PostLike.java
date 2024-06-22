package com.example.test.Model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostLike {
    private Long id;
    private Long postId;
    private String userId;
    private Timestamp createdAt;
}