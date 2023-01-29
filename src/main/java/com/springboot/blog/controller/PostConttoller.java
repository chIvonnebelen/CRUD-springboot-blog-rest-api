package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController   //internal uses at @Controller @ResponseBody, we use to convert a response that is a Java object into JSON
@RequestMapping("/api/posts")
public class PostConttoller {  // many developers use PostResource
   //we are injecting interface, not a class(loose coupling)
    private PostService postService;

    public PostConttoller(PostService postService) { //we omit @Autowired coz we have ONE constructor
        this.postService = postService;
    }
    //create blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
       return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all post rest api
    @GetMapping
    public List<PostDto> getALlPosts(){
        return postService.getAllPosts();
    }
    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    //update post by id rest api
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name="id") long id){
        PostDto postResponse =postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
    //delete post rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") long id){
        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);

    }
}
