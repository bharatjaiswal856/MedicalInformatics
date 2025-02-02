package com.example.medicalinformatics.controller;

import com.example.medicalinformatics.model.User;
import com.example.medicalinformatics.payload.JwtAuthenticationResponse;
import com.example.medicalinformatics.payload.LoginRequest;
import com.example.medicalinformatics.payload.SignUpRequest;
import com.example.medicalinformatics.payload.PasswordResetRequest;
import com.example.medicalinformatics.security.JwtTokenProvider;
import com.example.medicalinformatics.security.UserPrincipal;
import com.example.medicalinformatics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userPrincipal.getUsername(), userPrincipal.getRole()));
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", loginRequest.getUsername(), e);
            return ResponseEntity.badRequest().body("Invalid username or password");
        } catch (Exception e) {
            logger.error("An error occurred during authentication for user: {}", loginRequest.getUsername(), e);
            return ResponseEntity.internalServerError().body("An error occurred during authentication");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            if (userService.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Username is already taken!");
            }

            if (userService.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Email is already in use!");
            }

            User user = new User();
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(signUpRequest.getPassword());
            user.setRole(signUpRequest.getRole());
            user.setFirstName(signUpRequest.getFirstName());
            user.setLastName(signUpRequest.getLastName());
            user.setPhoneNumber(signUpRequest.getPhoneNumber());
            user.setAddress(signUpRequest.getAddress());

            User result = userService.createUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            logger.error("Registration failed for user: {}", signUpRequest.getUsername(), e);
            return ResponseEntity.internalServerError().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdminUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        try {
            if (userService.existsByUsername(username)) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Username is already taken!");
            }

            if (userService.existsByEmail(email)) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Email is already in use!");
            }

            User adminUser = userService.createAdminUser(username, email, password);
            return ResponseEntity.ok("Admin user created successfully");
        } catch (Exception e) {
            logger.error("Admin user creation failed for username: {}", username, e);
            return ResponseEntity.internalServerError().body("Admin user creation failed: " + e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> requestPasswordReset(@RequestBody PasswordResetRequest request) {
        try {
            userService.requestPasswordReset(request.getEmail());
            return ResponseEntity.ok("Password reset instructions sent to your registered email.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetRequest resetRequest) {
        try {
            userService.resetPassword(resetRequest.getToken(), resetRequest.getNewPassword());
            return ResponseEntity.ok("Password has been reset successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

