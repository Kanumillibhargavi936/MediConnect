package com.edutech.progressive.service;

import com.edutech.progressive.dto.UserRegistrationDTO;

import com.edutech.progressive.entity.User;

public interface UserLoginService {

    void registerUser(UserRegistrationDTO dto) throws Exception;

    User getUserByUsername(String username);

    User getUserDetails(int userId);

}
 