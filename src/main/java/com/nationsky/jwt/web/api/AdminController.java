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
@Authorized("ADMIN")
public class AdminController {

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public Map<String, String> admin(HttpServletRequest request) {

        User user = WebSecurityContext.getInstance().getCurrentUser();

        Map<String, String> response = new HashMap<>();

        response.put("currentUser", user.getDisplayName());
        response.put("data", "These are some admin resources.");

        return response;
    }
}
