package com.helloegor03.sub.repository;

import com.helloegor03.sub.model.UserMail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMailRepository extends JpaRepository<UserMail, Long> {
    Optional<UserMail> findByEmail(String email);
}
