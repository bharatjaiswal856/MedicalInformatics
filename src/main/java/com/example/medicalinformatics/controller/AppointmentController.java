package com.example.medicalinformatics.controller;

import com.example.medicalinformatics.model.Appointment;
import com.example.medicalinformatics.model.User;
import com.example.medicalinformatics.security.UserPrincipal;
import com.example.medicalinformatics.service.AppointmentService;
import com.example.medicalinformatics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAppointments(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        User user = userService.findById(currentUser.getId());
        List<Appointment> appointments;

        if ("ADMIN".equals(user.getRole())) {
            appointments = appointmentService.getAllAppointments(start, end);
        } else if ("DOCTOR".equals(user.getRole())) {
            appointments = appointmentService.getDoctorAppointments(user, start, end);
        } else {
            appointments = appointmentService.getPatientAppointments(user, start, end);
        }

        return ResponseEntity.ok(appointments);
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment, @AuthenticationPrincipal UserPrincipal currentUser) {
        try {
            User patient = userService.findById(currentUser.getId());
            appointment.setPatient(patient);

            // Fetch the doctor by ID
            User doctor = userService.findById(appointment.getDoctor().getId());
            if (doctor == null || !"DOCTOR".equals(doctor.getRole())) {
                return ResponseEntity.badRequest().body("Invalid doctor selected");
            }
            appointment.setDoctor(doctor);

            Appointment createdAppointment = appointmentService.createAppointment(appointment);
            return ResponseEntity.ok(createdAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating appointment: " + e.getMessage());
        }
    }
}

