package com.example.demo.blogapplication.service.implementation;

import com.example.demo.blogapplication.dto.CommentDto;
import com.example.demo.blogapplication.exception.BlogAPIException;
import com.example.demo.blogapplication.exception.ResourceNotFoundException;
import com.example.demo.blogapplication.model.Comment;
import com.example.demo.blogapplication.model.Post;
import com.example.demo.blogapplication.repository.CommentRepo;
import com.example.demo.blogapplication.repository.PostRepo;
import com.example.demo.blogapplication.service.repo.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImple implements CommentService {


    final
    CommentRepo commentRepo;

    final ModelMapper modelMapper;
    final
    PostRepo postRepo;

    @Autowired
    public CommentServiceImple(CommentRepo commentRepo, PostRepo postRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(Long id, CommentDto commentDto) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        //   Comment comment = mapToEntity(commentDto);
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment comment1 = commentRepo.save(comment);
        return mapToDto(comment1);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long id) {

        List<Comment> comment = commentRepo.findAllByPostId(id);
        return comment.stream().map(this::mapToDto).toList();
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto getUpdateCommentById(Long id, CommentDto commentDto) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("comment", "id", id));
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        Comment comment1 = commentRepo.save(comment);
        return mapToDto(comment1);
    }


    @Override
    public void getDeleteById(Long id) {
        commentRepo.deleteById(id);

    }

    private CommentDto mapToDto(Comment comment) {

//          commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setBody(comment.getBody()
//        commentDto.setEmail(comment.getEmail());
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {

//          comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
        return modelMapper.map(commentDto, Comment.class);
    }
}
