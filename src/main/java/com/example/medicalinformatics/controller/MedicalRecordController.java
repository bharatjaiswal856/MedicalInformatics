package com.example.medicalinformatics.controller;

import com.example.medicalinformatics.model.MedicalRecord;
import com.example.medicalinformatics.model.User;
import com.example.medicalinformatics.security.UserPrincipal;
import com.example.medicalinformatics.service.MedicalRecordService;
import com.example.medicalinformatics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createMedicalRecord(@RequestBody MedicalRecord medicalRecord, @AuthenticationPrincipal UserPrincipal currentUser) {
        User doctor = userService.findById(currentUser.getId());
        medicalRecord.setDoctor(doctor);
        MedicalRecord createdRecord = medicalRecordService.createMedicalRecord(medicalRecord);
        return ResponseEntity.ok(createdRecord);
    }

    @GetMapping("/patient")
    public ResponseEntity<?> getPatientMedicalRecords(@AuthenticationPrincipal UserPrincipal currentUser) {
        User patient = userService.findById(currentUser.getId());
        List<MedicalRecord> records = medicalRecordService.getPatientMedicalRecords(patient);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/doctor")
    public ResponseEntity<?> getDoctorMedicalRecords(@AuthenticationPrincipal UserPrincipal currentUser) {
        User doctor = userService.findById(currentUser.getId());
        List<MedicalRecord> records = medicalRecordService.getDoctorMedicalRecords(doctor);
        return ResponseEntity.ok(records);
    }
}

