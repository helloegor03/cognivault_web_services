package com.helloegor03.sub.service;

import com.helloegor03.sub.model.UserMail;
import com.helloegor03.sub.repository.UserMailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribersService {
    private final UserMailRepository userMailRepository;

    public SubscribersService(UserMailRepository userMailRepository) {
        this.userMailRepository = userMailRepository;

    }

    public UserMail saveNewSubscriber(String email){
        if(userMailRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("User is already subscribed");
        }
        UserMail userMail = new UserMail();
        userMail.setEmail(email);
        return userMailRepository.save(userMail);
    }

    public List<UserMail> getAllSubscribers(){
        if(userMailRepository.findAll().isEmpty()){
            throw new RuntimeException("U dont have subscribers");
        }
        return userMailRepository.findAll();
    }


}
