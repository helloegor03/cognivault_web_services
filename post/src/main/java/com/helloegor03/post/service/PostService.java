package com.helloegor03.post.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.helloegor03.post.dto.PostCreatedEvent;
import com.helloegor03.post.dto.PostRequest;
import com.helloegor03.post.model.Post;
import com.helloegor03.post.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final Cloudinary cloudinary;
    private final KafkaTemplate<String, PostCreatedEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final Jedis jedis;

    public PostService(PostRepository postRepository,
                       Cloudinary cloudinary,
                       KafkaTemplate<String, PostCreatedEvent> kafkaTemplate,
                       ObjectMapper objectMapper) {
        this.postRepository = postRepository;
        this.cloudinary = cloudinary;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.jedis = new Jedis("localhost", 6379);
    }

    public Post createPost(PostRequest input, Authentication authentication, MultipartFile file) throws IOException {
        Post post = new Post();
        post.setDate(LocalDate.now());
        post.setDescription(input.getDescription());
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

        Post saved = postRepository.save(post);

        String json = objectMapper.writeValueAsString(saved);
        jedis.set("posts::" + saved.getId(), json);

        PostCreatedEvent event = new PostCreatedEvent(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getTag(),
                saved.getAuthor()
        );
        kafkaTemplate.send("post-created-topic", event);

        return saved;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);

        jedis.del("posts::" + id);
    }

    public Post getPostById(Long id) {
        try {
            String json = jedis.get("posts::" + id);
            if (json != null) {
                System.out.println("Post fetched from Redis id=" + id);
                return objectMapper.readValue(json, Post.class); // возвращаем объект из Redis
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Fetching post from DB id=" + id);
        // Если в Редисе нет, то делаем запрос в бд
        return postRepository.findById(id).orElse(null);
    }
}
