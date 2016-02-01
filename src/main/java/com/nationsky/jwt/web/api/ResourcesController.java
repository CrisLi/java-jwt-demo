package com.nationsky.jwt.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nationsky.jwt.domain.User;
import com.nationsky.jwt.web.security.Authorized;
import com.nationsky.jwt.web.security.WebSecurityContext;

@RestController
public class ResourcesController {

    @RequestMapping(path = "/public", method = RequestMethod.GET)
    public Map<String, String> getPublicResource() {

        Map<String, String> response = new HashMap<>();
        response.put("data", "These are some public resources.");

        return response;
    }

    @Authorized
    @RequestMapping(path = "/protected", method = RequestMethod.GET)
    public Map<String, String> getProtectedResource(HttpServletRequest request) {

        User user = WebSecurityContext.getInstance().getCurrentUser();

        Map<String, String> response = new HashMap<>();

        response.put("currentUser", user.getDisplayName());
        response.put("data", "These are some protected resources.");

        return response;
    }
}
