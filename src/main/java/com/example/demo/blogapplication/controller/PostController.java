package com.example.demo.blogapplication.controller;

import com.example.demo.blogapplication.dto.PostDto;
import com.example.demo.blogapplication.model.AppConstants;
import com.example.demo.blogapplication.service.repo.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "Crud rest Api's for Post"
)
public class
PostController {

    final
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @Operation(
            summary = "create post rest Api",
            description = "create post rest Api to save new post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 created"
    )
    @SecurityRequirement(
            name = "bearer Authencitaion"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping()
    public ResponseEntity<PostDto> savePost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                     @RequestParam(value = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortOrder) {
        List<PostDto> posts = postService.getAllPost(pageNo, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {

        return new ResponseEntity<>(postService.postById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "bearer Authencitaion"
    )
    public ResponseEntity<PostDto> getUpdateById(@Valid @PathVariable(name = "id") Long id,
                                                 @RequestBody PostDto postDto) {

        return new ResponseEntity<>(postService.
                updateById(id, postDto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "bearer Authencitaion"
    )
    public ResponseEntity<String> getDeleteById(@PathVariable Long id) {
        postService.deleteById(id);
        return new ResponseEntity<>("Post deleted Successfully", HttpStatus.OK);
    }



    @GetMapping("/api/v1/posts/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId) {
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
}
