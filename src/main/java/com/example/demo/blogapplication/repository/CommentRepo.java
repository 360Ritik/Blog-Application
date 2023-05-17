package com.example.demo.blogapplication.repository;

import com.example.demo.blogapplication.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long postId);
}
