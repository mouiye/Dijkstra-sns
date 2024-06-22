package com.example.test.DAO;

import com.example.test.Model.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FriendDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Friend friend) {
        String sql = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, friend.getUserId(), friend.getFriendId());
    }

    public int delete(String userId, String friendId) {
        String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        return jdbcTemplate.update(sql, userId, friendId);
    }

    public List<Friend> getFollowersByUserId(String userId) {
        String sql = "SELECT * FROM friends WHERE friend_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new FriendRowMapper());
    }

    public List<Friend> getFollowingByUserId(String userId) {
        String sql = "SELECT * FROM friends WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new FriendRowMapper());
    }

    private static class FriendRowMapper implements RowMapper<Friend> {
        @Override
        public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
            Friend friend = new Friend();
            friend.setUserId(rs.getString("user_id"));
            friend.setFriendId(rs.getString("friend_id"));
            return friend;
        }
    }
}