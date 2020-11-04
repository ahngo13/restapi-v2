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

    //게시글 추가
    @Test
    public void createPost(){
        String title = "createTestTitle";
        String contents = "cerateTestContents";
        Post post = postRepository.save(Post.builder()
                .title(title)
                .contents(contents)
                .build());

        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContents()).isEqualTo(contents);
    }

    //게시글 목록 조회
    @Test
    public void getPostList(){
        String title = "test title";
        String contents = "test contents";
        for(int i=0; i<10; i++){
            postRepository.save(Post.builder()
                    .title(title)
                    .contents(contents)
                    .build());

        }

        List<Post> postsList =  postRepository.findAll();
        assertThat(postsList.size()>=10);
    }

    //특정 게시글 조회
   @Test
   public void getPost(){
        String title = "test title";
        String contents = "test contents";
        postRepository.save(Post.builder()
                .title(title)
                .contents(contents)
                .build());

        Optional<Post> post = postRepository.findById(1L);

        assertThat(post.get().getTitle()).isEqualTo(title);
        assertThat(post.get().getContents()).isEqualTo(contents);
    }

    //특정 게시글 수정
    @Test
    public void updatePost(){
        String title = "createTestTitle";
        String contents = "cerateTestContents";
        Post createPost = postRepository.save(Post.builder()
                .title(title)
                .contents(contents)
                .build());

        title = "updateTestTitle";
        contents = "updateTestContents";

        Post updatePost = postRepository.save(Post.builder()
                .postId(createPost.getPostId())
                .title(title)
                .contents(contents)
                .build());

        assertThat(updatePost.getTitle()).isEqualTo(title);
        assertThat(updatePost.getContents()).isEqualTo(contents);
    }

    //특정 게시글 삭제
    @Test
    public void deletePost(){
        String title = "createTestTitle";
        String contents = "cerateTestContents";
        Post createPost = postRepository.save(Post.builder()
                .title(title)
                .contents(contents)
                .build());

        Post post = Post.builder().postId(createPost.getPostId()).build();
        postRepository.delete(post);

        Optional<Post> findPost = postRepository.findById(createPost.getPostId());
        assertThat(findPost).isEmpty();
    }


}
