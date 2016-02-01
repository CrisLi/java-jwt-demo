package com.nationsky.jwt.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.jwt.component.JwtHelper;
import com.nationsky.jwt.domain.Credentials;
import com.nationsky.jwt.domain.Role;
import com.nationsky.jwt.domain.User;
import com.nationsky.jwt.web.security.UnauthorizedException;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Map<String, User> STATIC_USERS = new ConcurrentHashMap<>();

    static {

        User admin = new User();
        admin.setUsername("chris");
        admin.setPassword("123456");
        admin.setRole(Role.ADMIN);
        admin.setDisplayName("Chris Li");

        User user = new User();
        user.setUsername("kitty");
        user.setPassword("123456");
        user.setRole(Role.USER);
        user.setDisplayName("Kitty Yu");

        STATIC_USERS.put(admin.getUsername(), admin);
        STATIC_USERS.put(user.getUsername(), user);
    }

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public String auth(Credentials credentials) {

        User user = STATIC_USERS.get(credentials.getUsername());

        if (user == null) {
            throw new UnauthorizedException("No user found.");
        }

        // for the production code, the password should be hashed.
        if (user.getPassword().equals(credentials.getPassword())) {
            return jwtHelper.generateToken(user);
        }

        throw new UnauthorizedException("Invalid username or password.");
    }

}
