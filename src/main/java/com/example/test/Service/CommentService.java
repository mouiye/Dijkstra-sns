package com.example.test.Service;

import com.example.test.DAO.CommentDAO;
import com.example.test.DAO.CommentLikeDAO;
import com.example.test.Model.Comment;
import com.example.test.Model.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentDAO commentDAO;
    private final CommentLikeDAO commentLikeDAO;


    public int createComment(Long postId, String userId, String content) {
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return commentDAO.save(comment);
    }

    public int deleteComment(Long id) {
        return commentDAO.delete(id);
    }

    public int likeComment(Long id, String userId) {
        CommentLike commentLike = new CommentLike();
        commentLike.setCommentId(id);
        commentLike.setUserId(userId);

        return commentLikeDAO.save(commentLike);
    }

    public List<CommentLike> getCommentLikes(Long commentId) {
        return commentLikeDAO.findByCommentId(commentId);
    }

    public int unlikeComment(Long id, String userId) {
        return commentLikeDAO.delete(id, userId);
    }

    public List<Comment> findByPostId(Long postId) {
        return commentDAO.findByPostId(postId);
    }

    public Comment findById(Long id) {
        return commentDAO.findById(id);
    }
}