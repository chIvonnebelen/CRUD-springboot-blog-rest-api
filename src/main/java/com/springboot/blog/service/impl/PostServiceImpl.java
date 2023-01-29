package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    // no es necesario de @Autowired porque hay UN constructor
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert DTo to entity
      //  Post post= new Post();
      //  post.setTitle(postDto.getTitle());
      //  post.setDescription(postDto.getDescription());
      //  post.setContent(postDto.getContent());

        //convert DTo to entity
        Post post = mapToEntity(postDto);

        Post newPost= postRepository.save(post);

        // convert entity to DTO
        PostDto postResponse= mapToDto(newPost);


        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
       List<Post> posts= postRepository.findAll();
       return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);

        return mapToDto(updatePost);

    }

    @Override
    public void deletePostById(long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);

    }


    //convert Entity into DTO
    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;

    }
    private Post mapToEntity(PostDto postDto){
        //convert DTO to entity
        Post post= new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
