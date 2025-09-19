package com.helloegor03.sub.service;

import com.helloegor03.sub.model.UserMail;
import com.helloegor03.sub.repository.UserMailRepository;
import jakarta.mail.MessagingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KafkaConsumerService {

    private final UserMailRepository userMailRepository;
    private final EmailService emailService;

    public KafkaConsumerService(UserMailRepository userMailRepository, EmailService emailService) {
        this.userMailRepository = userMailRepository;
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "post-created-topic",
            groupId = "sub-service",
            containerFactory = "postCreatedKafkaListenerContainerFactory"
    )
    public void consume(Map<String, Object> event) {
        String postName = (String) event.get("name");
        String postDescription = (String) event.get("description");


        List<UserMail> subscribers = userMailRepository.findAll();

        for (UserMail subscriber : subscribers) {
            try {
                emailService.sendInformationAboutNewPost(
                        subscriber.getEmail(),
                        "NEW POST!",
                        "Check new post on CogniVault: " + postName + "\n\n" + postDescription
                );
            } catch (MessagingException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Отправлены уведомления подписчикам о посте: " + postName);
    }
}
