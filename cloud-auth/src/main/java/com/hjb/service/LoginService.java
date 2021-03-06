package com.hjb.service;

import com.hjb.util.AuthToken;

public interface LoginService {

    AuthToken login(String username, String password, String clientId, String clientSecret, String grandType);

    AuthToken refresh(String refresh_token,String clientId, String clientSecret, String grandType);
}
