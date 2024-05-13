package com.example.demo.blogapplication.controller;

import static io.unlogged.UnloggedTestUtils.*;
import static org.mockito.ArgumentMatchers.*;

import com.example.demo.blogapplication.dto.PostDto;
import com.example.demo.blogapplication.service.repo.PostService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.Exception;
import java.lang.String;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

public final class TestPostControllerV {
  private PostController postController;

  private PostService postService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setup() throws Exception {
    postService = Mockito.mock(PostService.class);
    postController = new PostController(postService);
  }

  @Test
  public void testMethodGetAllPosts() throws Exception {
    String sortBy = "sortBy";
    String sortOrder = "sortOrder";
    // 
    List<PostDto> allPost = objectMapper.readValue("[{\"id\": \"0\", \"title\": \"string\", \"description\": \"string\", \"content\": \"string\", \"categoryId\": \"0\"}]", new TypeReference<List<PostDto>>(){});
    Mockito.when(postService.getAllPost(eq(-1538890032), eq(1179111245), eq(sortBy), eq(sortOrder))).thenReturn(allPost);
    // 
    String sortBy1 = "sortBy";
    String sortOrder1 = "sortOrder";
    ResponseEntity<List<PostDto>> responseEntity = postController.getAllPosts(0, 0, sortBy1, sortOrder1);
    ResponseEntity<List<PostDto>> responseEntityExpected = null;
    Assertions.assertEquals(responseEntityExpected, responseEntity);
  }
}
