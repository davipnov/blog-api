package com.api.blog.controller;

import com.api.blog.dto.request.ComentarioRequestDto;
import com.api.blog.dto.request.PostRequestDto;
import com.api.blog.dto.response.ComentarioResponseDto;
import com.api.blog.dto.response.PostResponseDto;
import com.api.blog.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Posts e comentários", description = "Operações do Blog API")
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //LISTA TODOS OS POSTS
    @Operation(summary = "Lista posts com paginação")
    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "data") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
    	    @RequestParam(required = false) String titulo) {

    	 Sort sort = direction.equalsIgnoreCase("asc")
                 ? Sort.by(sortBy).ascending()
                 : Sort.by(sortBy).descending();
    	 
    	 Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(postService.findAll(pageable, titulo));
    }

    //LISTA UM POST
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.findById(id));
    }

   
    //ADICIONA NOVO POST
    @PostMapping("/newpost")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody @Valid PostRequestDto dto) {
        PostResponseDto created = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    //EDITA UM POST
    @PutMapping("/editapost/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable UUID id, @RequestBody @Valid PostRequestDto dto) {
        return ResponseEntity.ok(postService.updatePost(id, dto));
    }
    //DELETA UM POST
    @DeleteMapping("/deletapost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    
   // ########################################## COMENTARIOS #############################################
    @PostMapping("/comentarios/{postId}")
    public ResponseEntity<ComentarioResponseDto> addComentario(
            @PathVariable UUID postId,
            @RequestBody @Valid ComentarioRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.addComentario(postId, dto));
    }
//
//    @PutMapping("/comentarios/{comentarioId}")
//    public ResponseEntity<ComentarioResponseDto> updateComentario(
//            @PathVariable UUID comentarioId,
//            @RequestBody @Valid ComentarioRequestDto dto) {
//        return ResponseEntity.ok(postService.updateComentario(comentarioId, dto));
//    }

    @DeleteMapping("/deletacomentarios/{comentarioId}")
    public ResponseEntity<Void> deleteComentario(@PathVariable UUID comentarioId) {
        postService.deleteComentario(comentarioId);
        return ResponseEntity.noContent().build();
    }
}