package com.example.test.DAO;

import com.example.test.Model.CommentLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentLikeDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(CommentLike commentLike) {
        String sql = "INSERT INTO comment_likes (comment_id, user_id, created_at) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, commentLike.getCommentId(), commentLike.getUserId(), commentLike.getCreatedAt());
    }

    public int delete(Long commentId, String userId) {
        String sql = "DELETE FROM comment_likes WHERE comment_id = ? AND user_id = ?";
        return jdbcTemplate.update(sql, commentId, userId);
    }

    public List<CommentLike> findByCommentId(Long commentId) {
        String sql = "SELECT * FROM comment_likes WHERE comment_id = ?";
        return jdbcTemplate.query(sql, new Object[]{commentId}, new CommentLikeRowMapper());
    }

    private static class CommentLikeRowMapper implements RowMapper<CommentLike> {
        @Override
        public CommentLike mapRow(ResultSet rs, int rowNum) throws SQLException {
            CommentLike commentLike = new CommentLike();
            commentLike.setId(rs.getLong("id"));
            commentLike.setCommentId(rs.getLong("comment_id"));
            commentLike.setUserId(rs.getString("user_id"));
            commentLike.setCreatedAt(rs.getTimestamp("created_at"));
            return commentLike;
        }
    }
}