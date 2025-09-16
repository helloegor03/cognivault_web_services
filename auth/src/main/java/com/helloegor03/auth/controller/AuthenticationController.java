package com.helloegor03.auth.controller;

import com.helloegor03.auth.config.JwtUtil;
import com.helloegor03.auth.dto.*;
import com.helloegor03.auth.model.User;
import com.helloegor03.auth.service.AuthenticationService;
import com.helloegor03.auth.service.UserEventProducer;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserEventProducer userEventProducer;
    private final JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationService authenticationService, UserEventProducer userEventProducer, JwtUtil jwtUtil) {
        this.authenticationService = authenticationService;
        this.userEventProducer = userEventProducer;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserDto input) {
        try {
            User user = authenticationService.signUpRequest(input);
            userEventProducer.sendUserCreated(
                    new UserCreatedEvent(user.getId(), user.getEmail(), user.getUsername())
            );
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginUserDto input) {
        Authentication authentication = authenticationService.authenticate(input);
        String token = jwtUtil.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyUserDto input){
        authenticationService.verifyUser(input);
        return ResponseEntity.ok("Account verify successfully");
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resendCode(@RequestParam String email){
        authenticationService.resendVerificationCode(email);
        return ResponseEntity.ok("Verification code resent to " + email);
    }



}
