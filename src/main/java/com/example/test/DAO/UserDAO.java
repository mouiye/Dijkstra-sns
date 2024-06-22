package com.example.test.DAO;

import com.example.test.Model.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(SiteUser siteUser) {
        String sql = "INSERT INTO users (login_id, password, created_at) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, siteUser.getLoginId(), siteUser.getPassword(), siteUser.getCreatedAt());
    }

    public Optional<SiteUser> findByLoginId(String loginId) {
        String sql = "SELECT * FROM users WHERE login_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{loginId}, new UserRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static class UserRowMapper implements RowMapper<SiteUser> {
        @Override
        public SiteUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            SiteUser siteUser = new SiteUser();
            siteUser.setId(rs.getLong("id"));
            siteUser.setLoginId(rs.getString("login_id"));
            siteUser.setPassword(rs.getString("password"));
            siteUser.setCreatedAt(rs.getTimestamp("created_at"));
            return siteUser;
        }
    }
}