package com.example.medicalinformatics.controller;

import com.example.medicalinformatics.model.User;
import com.example.medicalinformatics.security.UserPrincipal;
import com.example.medicalinformatics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/doctors")
    public ResponseEntity<?> getDoctors() {
        List<User> doctors = userService.findAllDoctors();
        List<UserDto> doctorDtos = doctors.stream()
                .map(doctor -> new UserDto(doctor.getId(), doctor.getUsername()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(doctorDtos);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody User updatedUser, @AuthenticationPrincipal UserPrincipal currentUser) {
        User updated = userService.updateUserProfile(currentUser.getId(), updatedUser);
        return ResponseEntity.ok(updated);
    }

    private static class UserDto {
        private Long id;
        private String username;

        public UserDto(Long id, String username) {
            this.id = id;
            this.username = username;
        }

        // Getters
        public Long getId() { return id; }
        public String getUsername() { return username; }
    }
}

