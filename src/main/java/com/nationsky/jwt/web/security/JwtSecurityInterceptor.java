package com.nationsky.jwt.web.security;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nationsky.jwt.component.JwtHelper;
import com.nationsky.jwt.domain.User;

public class JwtSecurityInterceptor extends HandlerInterceptorAdapter {

    private static final String TOKEN_KEY = "Authorization";
    private static final String TOKEN_SCHEME = "Bearer";
    private static final String RESPONSE_TOKEN_KEY = "token";

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Authorized authorized = findAuthorized(handler);

        if (authorized == null) {
            return true;
        }

        String authorizationHeader = request.getHeader(TOKEN_KEY);

        if (authorizationHeader == null) {
            throw new ForbiddenException("Forbidden: No Authorization header was found.");
        }

        String[] parts = authorizationHeader.split(" ");

        if (parts.length != 2) {
            throw new UnauthorizedException("Unauthorized: Format is Authorization: Bearer [token]");
        }

        String scheme = parts[0];
        String token = parts[1];

        if (!TOKEN_SCHEME.equalsIgnoreCase(scheme)) {
            throw new UnauthorizedException("Unauthorized: Format is Authorization: Bearer [token]");
        }

        User user = jwtHelper.verifyToken(token);

        doAuth(user, authorized);
        WebSecurityContext.getInstance().setCurrentUser(user);

        String refreshedToken = jwtHelper.generateToken(user);
        response.setHeader(RESPONSE_TOKEN_KEY, refreshedToken);

        return true;
    }

    private Authorized findAuthorized(Object handler) {

        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;

            Authorized authorized = handlerMethod.getMethodAnnotation(Authorized.class);

            if (authorized != null) {
                return authorized;
            }

            return handlerMethod.getBean().getClass().getAnnotation(Authorized.class);
        }

        return null;
    }

    private void doAuth(User currentUser, Authorized authorized) {

        String[] allowedRoles = authorized.value();

        if (isAllowAllRoles(allowedRoles)) {
            return;
        }

        String role = currentUser.getRole().toString();

        if (!Arrays.asList(allowedRoles).contains(role)) {
            throw new UnauthorizedException("Unauthorized: Don't have enough privileges.");
        }

    }

    private boolean isAllowAllRoles(String[] allowedRoles) {
        if (allowedRoles == null || allowedRoles.length == 0) {
            return true;
        }
        return false;
    }
}
