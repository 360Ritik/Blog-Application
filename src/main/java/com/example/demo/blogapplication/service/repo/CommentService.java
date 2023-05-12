package com.example.demo.blogapplication.service.repo;

import com.example.demo.blogapplication.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long id, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(Long id);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto getUpdateCommentById(Long id, CommentDto commentDto);

    void getDeleteById(Long id);


}
