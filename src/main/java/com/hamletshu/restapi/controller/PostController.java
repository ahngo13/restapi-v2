package com.hamletshu.restapi.controller;

import com.hamletshu.restapi.entity.Post;
import com.hamletshu.restapi.entity.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//Controller 어노테이션 추가 (controller 인식)
@Controller
// value : localhost:8080/posts로 접근할 경우 이 controller에 접근한다.
// produces : 생산 가능한 미디어 타입을 지정해서 일치할 때만 요청을 매칭함. (매핑 제한용)
// comsumes : 소비 가능한 미디어 타입을 지정해서 때만 요청을 매칭함. (매핑 제한용)
@RequestMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    @Autowired
    private PostRepository postRepository;

    //게시글 추가
    //Post 요청이 들어왔을 때, value=""는 localhost:8080/posts를 의미함
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    //ResponseEntity 상태코드 제어
    public ResponseEntity<Void> createPost(@RequestBody Map<String, Object> requestBody){
        Post post = Post.builder().title(requestBody.get("title").toString())
                                  .contents(requestBody.get("contents").toString()).build();
        postRepository.save(post);

        //정상적으로 수행됐다고 상태 리턴 (200)
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //게시글 목록 조회
    //Get 요청이 들어왔을 때, value=""는 localhost:8080/posts를 의미함
    @GetMapping(value = "")
    //RequestParam : 넘어온 파라미터 가지고 올때 사용 required = false를 사용하면 필수값 아님을 의미
    public String getPostList(@RequestParam(value = "postId", required = false) Long postId, Model model){
        List<Post> posts= postRepository.findAll();
        model.addAttribute("posts", posts);
        return "index";
    }

    //게시글 추가/수정 페이지
    @GetMapping(value = "/add-post-page")
    public String getAddPostPage(@RequestParam(value = "state", required = false, defaultValue = "create")String state,
                                 @RequestParam(value = "postId", required = false) Long postId, Model model){
        if(state.equals("update")){
            Post post = postRepository.findById(postId).get();
            model.addAttribute("post", post);
        }

        model.addAttribute("state", state);
        model.addAttribute("postId", postId);

        return "add-post-page";
    }

    //특정 게시글 조회
    //Get 요청이 들어왔을 때, value = "/{postId}"는 postId 게시글의 데이터를 조회하기 위해 설정
    //ex) localhost:8080/posts/1
    @GetMapping(value = "/{postId}")
    //PathVariable은 URI에 넘어온 postId 값을 가져오기 위해 사용
    public Optional<Post> getPost(@PathVariable Long postId){
        Optional<Post> post = postRepository.findById(postId);

        return post;
    }

    //Put 요청이 들어왔을 때, value = "/{postId}"는 postId 게시글의 데이터를 수정하기 위해 설정
    @PutMapping(value = "/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePost(
            //PathVariable은 URI에 넘어온 postId 값을 가져오기 위해 사용
            @PathVariable Long postId, @RequestBody Map<String, Object> requestBody
            ){
        Post post = Post.builder().postId(postId).title(requestBody.get("title").toString()).contents(requestBody.get("contents").toString()).build();
        postRepository.save(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Delete 요청이 들어왔을 때, value = "/{postId}"는 postId 게시글의 데이터를 삭제하기 위해 설정
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId
    ){
        Post post = Post.builder().postId(postId).build();
        postRepository.delete(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}