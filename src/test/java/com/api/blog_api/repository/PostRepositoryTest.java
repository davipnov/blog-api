package com.api.blog_api.repository;

import com.api.blog.model.PostModel;
import com.api.blog.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository repository;

    @Test
    void deveSalvarERetornarPost() {

        //Arrange
        PostModel post = new PostModel("Henrique", "Alunos +devs", "Os alunos do +devs são os melhores");

        //Act
        PostModel postSalvo = repository.save(post);

        //Assert
        Assertions.assertThat(postSalvo).isNotNull();
    }
}
