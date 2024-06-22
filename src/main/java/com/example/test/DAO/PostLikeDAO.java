package com.example.test.DAO;

import com.example.test.Model.PostLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostLikeDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(PostLike postLike) {
        String sql = "INSERT INTO post_likes (post_id, user_id, created_at) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, postLike.getPostId(), postLike.getUserId(), postLike.getCreatedAt());
    }

    public int delete(Long postId, String userId) {
        String sql = "DELETE FROM post_likes WHERE post_id = ? AND user_id = ?";
        return jdbcTemplate.update(sql, postId, userId);
    }

    public List<PostLike> findByPostId(Long postId) {
        String sql = "SELECT * FROM post_likes WHERE post_id = ?";
        return jdbcTemplate.query(sql, new Object[]{postId}, new PostLikeRowMapper());
    }

    private static class PostLikeRowMapper implements RowMapper<PostLike> {
        @Override
        public PostLike mapRow(ResultSet rs, int rowNum) throws SQLException {
            PostLike postLike = new PostLike();
            postLike.setId(rs.getLong("id"));
            postLike.setPostId(rs.getLong("post_id"));
            postLike.setUserId(rs.getString("user_id"));
            postLike.setCreatedAt(rs.getTimestamp("created_at"));
            return postLike;
        }
    }
}