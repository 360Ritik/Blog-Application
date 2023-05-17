package com.example.demo.blogapplication.service.implementation;

import com.example.demo.blogapplication.Security.JwtTokenProvider;
import com.example.demo.blogapplication.dto.LoginDto;
import com.example.demo.blogapplication.dto.RegisterDto;
import com.example.demo.blogapplication.exception.BlogAPIException;
import com.example.demo.blogapplication.model.Role;
import com.example.demo.blogapplication.model.User;
import com.example.demo.blogapplication.repository.RoleRepository;
import com.example.demo.blogapplication.repository.UserRepository;
import com.example.demo.blogapplication.service.repo.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImple implements AuthService {

    final AuthenticationManager authenticationManager;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;

    final ModelMapper modelMapper;

    final
    UserRepository userRepository;

    final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImple(AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto, long time) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication, time);
    }


    @Override
    public String register(RegisterDto registerDto) {
        // add check for password and confirmPassword are same
//        if(!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
//           return "password and confirmPassword should be same!";
//        }
        // add check for username exists in database
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        // add check for email exists in database
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }
        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return "User Registered Successfully !";
    }
}
