package com.nationsky.jwt.web.security;

import com.nationsky.jwt.domain.User;

public class WebSecurityContext {

    private ThreadLocal<User> CURRENT_USER_HOLDER = new ThreadLocal<>();

    private static WebSecurityContext instance = new WebSecurityContext();

    private WebSecurityContext() {
    }

    public static WebSecurityContext getInstance() {
        return instance;
    }

    public void setCurrentUser(User user) {
        CURRENT_USER_HOLDER.set(user);
    }

    public User getCurrentUser() {
        return CURRENT_USER_HOLDER.get();
    }
}
