package com.example.demo.blogapplication.repository;

import com.example.demo.blogapplication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findByCategoryId(Long categoryId);

}
