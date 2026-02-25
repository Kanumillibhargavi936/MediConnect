package com.edutech.progressive.controller;

import com.edutech.progressive.dto.LoginRequest;
import com.edutech.progressive.dto.UserRegistrationDTO;
import com.edutech.progressive.entity.User;
import org.springframework.http.ResponseEntity;

public class UserLoginController {
    public ResponseEntity<User> registerUser(UserRegistrationDTO registrationDTO) {
        return null;
    }

    public ResponseEntity loginUser(LoginRequest loginRequest) {
        return null;
    }

    public ResponseEntity<?> getUserDetails(int userId) {
        return null;
    }
}
