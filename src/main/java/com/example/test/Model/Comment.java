package com.example.test.Model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Comment {
    private Long id;
    private Long postId;
    private String userId;
    private String content;
    private Timestamp createdAt;
    private List<CommentLike> likedBy;
    private Boolean isLikedByMe;
}