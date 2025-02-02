package com.example.medicalinformatics.repository;

import com.example.medicalinformatics.model.Appointment;
import com.example.medicalinformatics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorAndAppointmentTimeBetween(User doctor, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByPatientAndAppointmentTimeBetween(User patient, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);
}

