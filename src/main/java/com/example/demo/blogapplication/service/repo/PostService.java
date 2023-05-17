package com.example.demo.blogapplication.service.repo;

import com.example.demo.blogapplication.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPost(int pageNo, int PageSize, String sortBy, String sortOrder);

    PostDto postById(Long id);

    PostDto updateById(Long id, PostDto postDto);

    void deleteById(Long id);

    List<PostDto> getPostByCategory(Long categoryID);

}
