package com.example.test.Model;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class CommentLike {
    private Long id;
    private Long commentId;
    private String userId;
    private Timestamp createdAt;
}
