package com.example.medicalinformatics.repository;

import com.example.medicalinformatics.model.MedicalRecord;
import com.example.medicalinformatics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatient(User patient);
    List<MedicalRecord> findByDoctor(User doctor);
}

