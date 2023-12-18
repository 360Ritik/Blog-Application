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
@RequestMapping("/api/v1/comments/")
public class CommentController {

    final
    CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/new/{postId}")
    public ResponseEntity<CommentDto> createComments(@Valid @PathVariable(value = "postId") Long postId,
                                                     @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/get/{pId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long pId) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(pId), HttpStatus.OK);
    }

    @GetMapping("/get/{commentId}")
    public ResponseEntity<CommentDto> getCommentsById(@PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(commentId), HttpStatus.OK);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDto> updateComments(@Valid @PathVariable(value = "commentId") Long Id,
                                                     @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.getUpdateCommentById(Id, commentDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteById(@PathVariable Long commentId) {
        commentService.getDeleteById(commentId);
        return new ResponseEntity<>("Comment deleted Successfully", HttpStatus.OK);
    }
}
