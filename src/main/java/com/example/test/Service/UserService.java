package com.example.test.Service;

import com.example.test.DAO.UserDAO;
import com.example.test.Model.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserDAO userDAO;

    public int register(String loginId, String password) {
        SiteUser siteUser = new SiteUser();
        siteUser.setLoginId(loginId);
        siteUser.setPassword(passwordEncoder.encode(password));
        siteUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return userDAO.save(siteUser);
    }

    public Optional<SiteUser> findByLoginId(String loginId) {
        return userDAO.findByLoginId(loginId);
    }
}