package com.example.test.DAO;

import com.example.test.Model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Comment comment) {
        String sql = "INSERT INTO comments (post_id, user_id, content, created_at) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, comment.getPostId(), comment.getUserId(), comment.getContent(), comment.getCreatedAt());
    }

    public Comment findById(Long id) {
        String sql = "SELECT * FROM comments WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CommentRowMapper());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM comments WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Comment> findByPostId(Long postId) {
        String sql = "SELECT * FROM comments WHERE post_id = ?";
        return jdbcTemplate.query(sql, new Object[]{postId}, new CommentRowMapper());
    }

    private static class CommentRowMapper implements RowMapper<Comment> {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setPostId(rs.getLong("post_id"));
            comment.setUserId(rs.getString("user_id"));
            comment.setContent(rs.getString("content"));
            comment.setCreatedAt(rs.getTimestamp("created_at"));
            return comment;
        }
    }
}