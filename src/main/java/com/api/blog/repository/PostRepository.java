package com.api.blog.repository;

import com.api.blog.model.PostModel;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostModel, UUID> {

    Page<PostModel> findByTituloContainingIgnoreCase(
            String titulo,
            Pageable pageable
    );
}