package com.example.medicalinformatics.controller;

import com.example.medicalinformatics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestParam String username, @RequestParam String newPassword) {
        try {
            userService.adminUpdatePassword(username, newPassword);
            return ResponseEntity.ok("Password updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

