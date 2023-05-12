package com.example.demo.blogapplication.repository;


import com.example.demo.blogapplication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}