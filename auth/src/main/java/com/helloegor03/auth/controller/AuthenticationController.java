package com.helloegor03.auth.controller;

import com.helloegor03.auth.dto.LoginUserDto;
import com.helloegor03.auth.dto.RegisterUserDto;
import com.helloegor03.auth.dto.VerifyUserDto;
import com.helloegor03.auth.model.User;
import com.helloegor03.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserDto input){
        User user = authenticationService.signUpRequest(input);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginUserDto input){
        User user = authenticationService.authenticate(input);
        return ResponseEntity.ok(user);
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
