package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.UserRegistrationDTO;
import com.edutech.progressive.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserLoginServiceImpl extends UserDetailsService {

    User createUser(User user) throws Exception;

    User getUserByUsername(String username) throws Exception;

    void registerUser(UserRegistrationDTO userRegistrationDTO) throws Exception;

    User getUserDetails(int userId);
}
