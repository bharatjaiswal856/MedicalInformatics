package com.example.medicalinformatics.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @Column(nullable = false)
    private LocalDateTime recordDate;

    @Column(length = 1000)
    private String diagnosis;

    @Column(length = 1000)
    private String treatmentPlan;

    @Column(length = 1000)
    private String prescription;

    // Getters and setters are handled by Lombok's @Data annotation
}

