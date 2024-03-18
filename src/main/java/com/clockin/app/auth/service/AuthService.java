package com.clockin.app.auth.service;

import com.clockin.app.user.User;

public interface AuthService {
    String generateJwtToken(User user);

    boolean validateMagicLinkToken(String token);

}
