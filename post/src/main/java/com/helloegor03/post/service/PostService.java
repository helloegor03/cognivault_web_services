package com.helloegor03.post.service;

import com.helloegor03.post.dto.PostRequest;
import com.helloegor03.post.model.Post;
import com.helloegor03.post.repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(PostRequest input, Authentication authentication){
        Post post = new Post();
        post.setDate(LocalDate.now());
        post.setDescripton(input.getDescripton());
        post.setName(input.getName());
        post.setTag(input.getTag());
        post.setText(input.getText());
        post.setImageUrl(input.getImageUrl());
        post.setAuthor(authentication.getName());

        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }




}
