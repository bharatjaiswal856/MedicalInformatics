package com.example.medicalinformatics.service;

import com.example.medicalinformatics.model.Appointment;
import com.example.medicalinformatics.model.User;
import com.example.medicalinformatics.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(Appointment appointment) {
        // TODO: Add validation logic here
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByAppointmentTimeBetween(start, end);
    }

    public List<Appointment> getDoctorAppointments(User doctor, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDoctorAndAppointmentTimeBetween(doctor, start, end);
    }

    public List<Appointment> getPatientAppointments(User patient, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByPatientAndAppointmentTimeBetween(patient, start, end);
    }
}

