package com.example.medicalinformatics.repository;

import com.example.medicalinformatics.model.PatientData;
import com.example.medicalinformatics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientDataRepository extends JpaRepository<PatientData, Long> {
    Optional<PatientData> findByUser(User user);
}

