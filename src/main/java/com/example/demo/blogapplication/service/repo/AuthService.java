package com.example.demo.blogapplication.service.repo;

import com.example.demo.blogapplication.dto.LoginDto;
import com.example.demo.blogapplication.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto, long time);

    String register(RegisterDto registerDto);
}