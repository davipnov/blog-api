package com.api.blog.service;

import com.api.blog.dto.request.ComentarioRequestDto;
import com.api.blog.dto.request.PostRequestDto;
import com.api.blog.dto.response.ComentarioResponseDto;
import com.api.blog.dto.response.PostResponseDto;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
   // List<PostResponseDto> findAll();
    Page<PostResponseDto> findAll(Pageable pageable);
    Page<PostResponseDto>findAll(Pageable pageable, String titulo);
    PostResponseDto findById(UUID id);
    PostResponseDto createPost(PostRequestDto dto);
    
    
    
    
    PostResponseDto updatePost(UUID id, PostRequestDto dto);
    void deletePost(UUID id);
    
    
    
    
    ComentarioResponseDto addComentario(UUID postId, ComentarioRequestDto dto);
    //ComentarioResponseDto updateComentario(UUID comentarioId, ComentarioRequestDto dto);
    void deleteComentario(UUID comentarioId);
   
}