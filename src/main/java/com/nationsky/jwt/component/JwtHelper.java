package com.nationsky.jwt.component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nationsky.jwt.domain.Role;
import com.nationsky.jwt.domain.User;
import com.nationsky.jwt.web.security.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.expire}")
    private Long expire;

    private SignatureAlgorithm algorithm;

    public JwtHelper() {
        this.algorithm = SignatureAlgorithm.HS512;
    }

    public String generateToken(User user) {

        return Jwts.builder() //
                .setClaims(userToClaims(user)) //
                .setExpiration(afterSeconds(expire)) //
                .signWith(algorithm, key) //
                .compact();

    }

    public User verifyToken(String token) {
        try {
            Jws<Claims> jwt = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return claimsToUser(jwt.getBody());
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    private Map<String, Object> userToClaims(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());
        claims.put("displayName", user.getDisplayName());

        return claims;
    }

    private User claimsToUser(Claims claims) {

        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);
        String displayName = claims.get("displayName", String.class);

        User user = new User();
        user.setUsername(username);
        user.setRole(Role.valueOf(role));
        user.setDisplayName(displayName);

        return user;
    }

    private Date afterSeconds(long seconds) {
        return Date.from(LocalDateTime.now().plusSeconds(seconds).atZone(ZoneId.systemDefault()).toInstant());
    }
}
