package com.example.test.DAO;

import com.example.test.Model.Post;
import com.example.test.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class PostDAO {

    private final JdbcTemplate jdbcTemplate;

    public int saveWithData(Post post) {
        String sql = "INSERT INTO posts (user_id, content, data, created_at) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, post.getUserId(), post.getContent(), post.getData(), post.getCreatedAt());
    }

    public int saveWithoutData(Post post) {
        String sql = "INSERT INTO posts (user_id, content, created_at) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, post.getUserId(), post.getContent(), post.getCreatedAt());
    }

    public Post findById(Long id) {
        String sql = "SELECT posts.id, posts.user_id, posts.content, posts.data, posts.created_at FROM posts WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new PostRowMapper());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int update(Long id, String content, byte[] image) {
        String sql = "UPDATE posts SET content = ?, data = ? WHERE id = ?";
        return jdbcTemplate.update(sql, content, image, id);
    }

    public int update(Long id, String content) {
        String sql = "UPDATE posts SET content = ? WHERE id = ?";
        return jdbcTemplate.update(sql, content, id);
    }

    public List<Post> findByUserId(String userId) {
        String sql = "SELECT posts.id, posts.user_id, posts.content, posts.data, posts.created_at FROM posts JOIN users ON posts.user_id = users.login_id WHERE users.login_id = ? ORDER BY posts.created_at DESC";
        return jdbcTemplate.query(sql, new Object[]{userId}, new PostRowMapper());
    }

    public List<Post> getAllPosts() {
        String sql = "SELECT posts.id, posts.user_id, posts.content, posts.data, posts.created_at FROM posts JOIN users ON posts.user_id = users.login_id ORDER BY posts.created_at DESC";
        return jdbcTemplate.query(sql, new PostRowMapper());
    }

    private static class PostRowMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setUserId(rs.getString("user_id"));
            post.setContent(rs.getString("content"));
            byte[] dataBytes = rs.getBytes("data");
            if (dataBytes != null) {
                post.setDataBase64(Base64.getEncoder().encodeToString(dataBytes));
            }
            post.setCreatedAt(rs.getTimestamp("created_at"));
            post.setIsDataImage(new FileService().isImage(dataBytes));
            return post;
        }
    }
}
