package com.api.blog.mapper;

import com.api.blog.dto.request.PostRequestDto;
import com.api.blog.dto.response.ComentarioResponseDto;
import com.api.blog.dto.response.PostResponseDto;
import com.api.blog.model.ComentarioModel;
import com.api.blog.model.PostModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostModel toEntity(PostRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new PostModel(
            dto.autor(),
            dto.titulo(),
            dto.texto()
        );
    }

    public PostResponseDto toDto(PostModel entity) {
        if (entity == null) {
            return null;
        }  
     // Substituição do Stream/Lambda por instrução 'if' e laço 'for' tradicional
        List<ComentarioResponseDto> comentariosDto = new ArrayList<>();

        if (entity.getComentarios() != null) {
            for (ComentarioModel comentario : entity.getComentarios()) {
                ComentarioResponseDto dto = ComentarioMapper.toResponseDto(comentario);
                comentariosDto.add(dto);
            }
        }
        return new PostResponseDto(
            entity.getId(),
            entity.getAutor(),
            entity.getData(),
            entity.getTitulo(),
            entity.getTexto(),
            comentariosDto
        );
    }

    public void updateEntityFromDto(PostRequestDto dto, PostModel entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setAutor(dto.autor());
        entity.setTitulo(dto.titulo());
        entity.setTexto(dto.texto());
    }
}
