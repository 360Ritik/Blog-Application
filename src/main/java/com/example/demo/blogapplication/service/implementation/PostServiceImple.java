package com.example.demo.blogapplication.service.implementation;

import com.example.demo.blogapplication.dto.PostDto;
import com.example.demo.blogapplication.exception.ResourceNotFoundException;
import com.example.demo.blogapplication.model.Post;
import com.example.demo.blogapplication.repository.PostRepo;
import com.example.demo.blogapplication.service.repo.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImple implements PostService {

    final PostRepo postRepo;

    final ModelMapper modelMapper;


    public PostServiceImple(PostRepo postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);

        Post newPost = postRepo.save(post);
        return mapToDTO(newPost);
    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> post = postRepo.findAll(pageable);
        List<Post> postList = post.getContent();
        return postList.stream().map(this::mapToDTO).collect(Collectors.toList());

    }

    @Override
    public PostDto postById(Long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updateById(Long id, PostDto postDto) {

        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post postResponse = postRepo.save(post);
        return mapToDTO(postResponse);
    }

    @Override
    public void deleteById(Long id) {
        postRepo.deleteById(id);
    }

    // convert Entity into DTO
    private PostDto mapToDTO(Post post) {
        //        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return modelMapper.map(post, PostDto.class);
    }

    // convert DTO to entity
    private Post mapToEntity(PostDto postDto) {
        //        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return modelMapper.map(postDto, Post.class);
    }

}
