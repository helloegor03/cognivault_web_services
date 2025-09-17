package com.helloegor03.post.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.helloegor03.post.dto.PostRequest;
import com.helloegor03.post.model.Post;
import com.helloegor03.post.repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final Cloudinary cloudinary;


    public PostService(PostRepository postRepository, Cloudinary cloudinary) {
        this.postRepository = postRepository;
        this.cloudinary = cloudinary;
    }

    public Post createPost(PostRequest input, Authentication authentication, MultipartFile file) throws IOException {
        Post post = new Post();
        post.setDate(LocalDate.now());
        post.setDescripton(input.getDescripton());
        post.setName(input.getName());
        post.setTag(input.getTag());
        post.setText(input.getText());
        post.setImageUrl(input.getImageUrl());
        post.setAuthor(authentication.getName());

        if (file != null && !file.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = uploadResult.get("secure_url").toString();
            post.setImageUrl(imageUrl);
        }

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

    public Optional<Post> getPostById(Long id){
        if(!postRepository.existsById(id)){
            throw new RuntimeException("Post not found");
        }
        return postRepository.findById(id);
    }




}
