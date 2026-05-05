package com.example.auth_service.service;


import com.example.auth_service.dto.request.UserRegistrationDto;

public interface UserService {
    void createUser(UserRegistrationDto  userRegistrationDto);
}
