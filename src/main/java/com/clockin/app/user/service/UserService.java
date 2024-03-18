package com.clockin.app.user.service;

import com.clockin.app.user.User;

public interface UserService {
    void registerUser(User newUser);

    void initiateLogin(String email);

    User getUserById(String userId);

    User getUserFromToken(String token);

    void saveUser(User user);
}
