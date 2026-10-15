package com.api.blog.mapper;

import org.springframework.stereotype.Component;

import com.api.blog.dto.request.ComentarioRequestDto;
import com.api.blog.dto.response.ComentarioResponseDto;
import com.api.blog.model.ComentarioModel;
import com.api.blog.model.PostModel;

@Component
public class ComentarioMapper {

    public static ComentarioResponseDto toResponseDto(ComentarioModel comentario) {
        if (comentario == null) return null;
        return new ComentarioResponseDto(
            comentario.getId(),
            comentario.getData(),
            comentario.getComentario()
        );
    }
    
    public ComentarioModel toEntity(ComentarioRequestDto dto, PostModel entity) {
        if (dto == null) {
            return null;
        }
        return new ComentarioModel(
            dto.comentario().toString(),
            entity
        );
    }
    
    public ComentarioResponseDto toDto(ComentarioModel entity) {
        if (entity == null) {
            return null;
        }
        return new ComentarioResponseDto(
            entity.getId(),
            entity.getData(),
            entity.getComentario()
        );
    }
}
