package com.example.test.Model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Post {
    private Long id;
    private String userId;
    private String content;
    private byte[] data;
    private String dataBase64;
    private Boolean isDataImage;
    private Timestamp createdAt;
    private String timeAgo;
    private List<PostLike> likedBy;
    private Boolean isLikedByMe;
    private List<Comment> comments;

}
