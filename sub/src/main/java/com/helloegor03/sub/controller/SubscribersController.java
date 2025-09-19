package com.helloegor03.sub.controller;

import com.helloegor03.sub.model.UserMail;
import com.helloegor03.sub.service.SubscribersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscribers")
public class SubscribersController {

    private final SubscribersService subscribersService;

    public SubscribersController(SubscribersService subscribersService) {
        this.subscribersService = subscribersService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<UserMail> subscribe(@RequestParam String email) {
        try {
            UserMail userMail = subscribersService.saveNewSubscriber(email);
            return ResponseEntity.ok(userMail);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserMail>> getAllSubscribers() {
        List<UserMail> subscribers = subscribersService.getAllSubscribers();
        return ResponseEntity.ok(subscribers);
    }
}
