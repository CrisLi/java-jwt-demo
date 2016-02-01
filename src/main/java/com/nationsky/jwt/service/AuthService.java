package com.nationsky.jwt.service;

import com.nationsky.jwt.domain.Credentials;

public interface AuthService {

    public String auth(Credentials credentials);
}
