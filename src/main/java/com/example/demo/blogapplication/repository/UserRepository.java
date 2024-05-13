package com.example.demo.blogapplication.repository;

import com.example.demo.blogapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//   Optional<User> findByEmail(String email);
//
//    Optional<User> findByNameOrEmail(String username, String email);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByNameOrEmail(String name, String email);
}