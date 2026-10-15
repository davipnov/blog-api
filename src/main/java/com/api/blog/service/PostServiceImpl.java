package com.api.blog.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.blog.dto.request.ComentarioRequestDto;
import com.api.blog.dto.request.PostRequestDto;
import com.api.blog.dto.response.ComentarioResponseDto;
import com.api.blog.dto.response.PostResponseDto;
import com.api.blog.exception.ResourceNotFoundException;
import com.api.blog.mapper.ComentarioMapper;
import com.api.blog.mapper.PostMapper;
import com.api.blog.model.ComentarioModel;
import com.api.blog.model.PostModel;
import com.api.blog.repository.ComentarioRepository;
import com.api.blog.repository.PostRepository;

//regras de negocio podem serem colocadas nessa classe de serviço
//ela serve como uma camada intermediaria entre o controler e repository
//diminuir o acoplamento

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;
    private final MessageService messageService;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper,
    		ComentarioRepository comentarioRepository,
			ComentarioMapper comentarioMapper, MessageService messageService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.comentarioRepository = comentarioRepository;
        this.comentarioMapper = comentarioMapper;
        this.messageService = messageService;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<PostResponseDto> findAll() {
//        List<PostModel> posts = postRepository.findAll();
//        List<PostResponseDto> dtos = new ArrayList<>();
//
//        for (PostModel post : posts) {
//            dtos.add(postMapper.toDto(post));
//        }
//        return dtos;
//    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAll(Pageable pageable) {

        Page<PostModel> posts = postRepository.findAll(pageable);

        return posts.map(post -> postMapper.toDto(post));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAll(Pageable pageable, String titulo) {

        Page<PostModel> posts;
        if (titulo == null || titulo.isBlank()) {
            posts = postRepository.findAll(pageable); }
        else {
            posts = (Page<PostModel>) postRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        }
        return posts.map(postMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto findById(UUID id) {
        PostModel post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("post.nao.encontrado", id)));
        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostResponseDto createPost(PostRequestDto dto) {
        PostModel post = postMapper.toEntity(dto);
        PostModel saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(UUID id, PostRequestDto dto) {
        PostModel post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("post.nao.encontrado", id)));
        postMapper.updateEntityFromDto(dto, post);

        PostModel updatedPost = postRepository.save(post);
        return postMapper.toDto(updatedPost);
    }

    @Override
    @Transactional
    public void deletePost(UUID id) {
        PostModel post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("post.nao.encontrado", id)));
        postRepository.delete(post);
    }
    
    
    //###############################COMENTARIOS ##########################################
    
    @Override
    @Transactional
    public ComentarioResponseDto addComentario(UUID postId, ComentarioRequestDto dto) {
	    PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("post.nao.encontrado", postId)));
    	ComentarioModel comentario = new ComentarioModel(dto.comentario(), post);
        post.adicionarComentario(comentario);
        ComentarioModel saved = comentarioRepository.save(comentario);
        return comentarioMapper.toDto(saved);
    }
   
    @Override
    @Transactional
    public void deleteComentario(UUID comentarioId) {
    	
	     ComentarioModel comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("comentario.nao.encontrado", comentarioId)));
         comentarioRepository.delete(comentario);
    }
}
