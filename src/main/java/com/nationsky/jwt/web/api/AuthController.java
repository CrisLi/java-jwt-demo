package com.nationsky.jwt.web.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nationsky.jwt.domain.Credentials;
import com.nationsky.jwt.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Map<String, String> login(@RequestBody Credentials credentials) {

        Map<String, String> response = new HashMap<>();

        String token = authService.auth(credentials);
        response.put("token", token);

        return response;
    }
}
