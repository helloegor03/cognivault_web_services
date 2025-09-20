package com.helloegor03.post.service;

import com.helloegor03.post.model.VerUser;
import com.helloegor03.post.repository.VerUserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserEventConsumer {
    private final VerUserRepository verUserRepository;

    public UserEventConsumer(VerUserRepository verUserRepository) {
        this.verUserRepository = verUserRepository;
    }

    @KafkaListener(
            topics = "user-created-topic",
            groupId = "post-service",
            containerFactory = "userCreatedKafkaListenerContainerFactory"
    )
    public void consume(Map<String, Object> event) {
        Long userId = ((Number) event.get("userId")).longValue();
        String username = (String) event.get("username");
        String email = (String) event.get("email");

        VerUser verUser = new VerUser();
        verUser.setEmail(email);
        verUser.setUserId(userId);
        verUser.setUsername(username);

        verUserRepository.save(verUser);
        System.out.println("Event saved: " + username);
    }
}
