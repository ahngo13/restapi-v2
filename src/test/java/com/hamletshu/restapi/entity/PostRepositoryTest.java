package com.hamletshu.restapi.entity;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//Junit이 없으면 pom.xml에 추가
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

//    @Test
//    @After
//    @Ignore
    public void cleanup(){
        postRepository.deleteAll();
    }

    @Test
    public void getPostOne(){
        String title = "test title";
        String contents = "test contents";
        postRepository.save(Post.builder()
                .title(title)
                .contents(contents)
                .build());

        Optional<Post> post = postRepository.findById(1L);
        System.out.println(post.get().getPostId().toString());
        System.out.println(post.get().getTitle());
        System.out.println(post.get().getContents());

        assertThat(post.get().getTitle()).isEqualTo(title);
        assertThat(post.get().getContents()).isEqualTo(contents);
    }

    @Test
    public void getPostAll(){

        String title = "test title";
        String contents = "test contents";
        postRepository.save(Post.builder()
                .title(title)
                .contents(contents)
                .build());

        List<Post> postsList =  postRepository.findAll();
        Post post = postsList.get(0);
        System.out.println(post.getPostId().toString());
        System.out.println(post.getTitle());
        System.out.println(post.getContents());

    }
}
