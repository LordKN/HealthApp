package com.HealthApp.service;

import com.HealthApp.model.MedicalHistory;
import com.HealthApp.repo.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository repo;

    public List<MedicalHistory> getAllMedicalHistory() {
        return repo.findAll();
    }

    public MedicalHistory getMedicalHistoryById (Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical History not found"));
    }

    public void saveMedicalHistory(MedicalHistory history) {
        repo.save(history);
    }

    public void deleteMedicalHistory(Long id) {
        repo.deleteById(id);
    }

    public long countMedicalHistory() {
        return repo.count();
    }

    public void deleteAllMedicalHistory() {
        repo.deleteAll();
    }
}
