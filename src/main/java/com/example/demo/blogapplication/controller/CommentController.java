package com.example.demo.blogapplication.controller;

import com.example.demo.blogapplication.dto.CommentDto;
import com.example.demo.blogapplication.service.repo.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    final
    CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComments(@Valid @PathVariable(value = "postId") Long postId,
                                                     @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{pId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long pId) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(pId), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentsById(@PathVariable Long postId,
                                                      @PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @PutMapping("/posts/{id}/comment")
    public ResponseEntity<CommentDto> updateComments(@Valid @PathVariable(value = "id") Long Id,
                                                     @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.getUpdateCommentById(Id, commentDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{Id}/comments")
    public ResponseEntity<String> deleteById(@PathVariable Long Id) {
        commentService.getDeleteById(Id);
        return new ResponseEntity<>("Comment deleted Successfully", HttpStatus.OK);
    }
}
