package com.example.medicalinformatics.service;

import com.example.medicalinformatics.model.PatientData;
import com.example.medicalinformatics.model.User;
import com.example.medicalinformatics.repository.PatientDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientDataService {

    @Autowired
    private PatientDataRepository patientDataRepository;

    public PatientData savePatientData(User user, PatientData patientData) {
        patientData.setUser(user);
        return patientDataRepository.save(patientData);
    }

    public PatientData getPatientDataByUser(User user) {
        return patientDataRepository.findByUser(user)
                .orElse(new PatientData());
    }
}

