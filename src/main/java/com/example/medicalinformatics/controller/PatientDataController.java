package com.example.medicalinformatics.controller;

import com.example.medicalinformatics.model.PatientData;
import com.example.medicalinformatics.model.User;
import com.example.medicalinformatics.security.UserPrincipal;
import com.example.medicalinformatics.service.PatientDataService;
import com.example.medicalinformatics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientDataController {

    @Autowired
    private PatientDataService patientDataService;

    @Autowired
    private UserService userService;

    @PostMapping("/data")
    public ResponseEntity<?> savePatientData(@AuthenticationPrincipal UserPrincipal currentUser,
                                             @RequestBody PatientData patientData) {
        User user = userService.findById(currentUser.getId());
        PatientData savedData = patientDataService.savePatientData(user, patientData);
        return ResponseEntity.ok(savedData);
    }

    @GetMapping("/data")
    public ResponseEntity<?> getPatientData(@AuthenticationPrincipal UserPrincipal currentUser) {
        User user = userService.findById(currentUser.getId());
        PatientData patientData = patientDataService.getPatientDataByUser(user);
        return ResponseEntity.ok(patientData);
    }
}

