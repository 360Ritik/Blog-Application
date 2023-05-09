package com.example.demo.blogapplication.controller;

import com.example.demo.blogapplication.dto.PostDto;
import com.example.demo.blogapplication.model.AppConstants;
import com.example.demo.blogapplication.service.repo.PostService;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    final
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<PostDto> savePost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(value ="pageNo",defaultValue =AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageNo,
                                                     @RequestParam(value ="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
                                                     @RequestParam(value ="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false)String sortBy,
                                                     @RequestParam(value ="sortOrder",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortOrder){
        List<PostDto> posts = postService.getAllPost(pageNo,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto>getPostById(@PathVariable(name = "id") Long id){

        return new ResponseEntity<>(postService.postById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto>getUpdateById(@PathVariable(name = "id") Long id,
                                                @RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.updateById(id,postDto),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> getDeleteById(@PathVariable Long id){
                            postService.deleteById(id);
        return  new ResponseEntity<>("Post deleted Successfully",HttpStatus.OK);
    }
}
