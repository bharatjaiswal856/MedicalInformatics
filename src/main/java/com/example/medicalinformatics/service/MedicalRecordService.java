package com.example.medicalinformatics.service;

import com.example.medicalinformatics.model.MedicalRecord;
import com.example.medicalinformatics.model.User;
import com.example.medicalinformatics.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public List<MedicalRecord> getPatientMedicalRecords(User patient) {
        return medicalRecordRepository.findByPatient(patient);
    }

    public List<MedicalRecord> getDoctorMedicalRecords(User doctor) {
        return medicalRecordRepository.findByDoctor(doctor);
    }
}

