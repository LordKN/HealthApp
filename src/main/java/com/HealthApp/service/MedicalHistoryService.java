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

        if (!repo.existsById(id)) {
            throw new RuntimeException("No medical history found to be deleted");
        }
        repo.deleteById(id);
    }

    public long countMedicalHistory() {
        return repo.count();
    }

    public void deleteAllMedicalHistory() {

        if (countMedicalHistory() == 0) {
            throw new RuntimeException("No medical history to be deleted");
        }
        repo.deleteAll();
    }

    private void validateMedicalHistory (MedicalHistory history) {
        if (history.getName() == null || history.getName().isBlank()) {
            throw new RuntimeException("Medical history's name needed");
        }

        if (history.getDesc() == null || history.getDesc().isBlank()) {
            throw new RuntimeException("Medical history's description needed");
        }

        if (history.getDesc().length() > 3000) {
            throw new RuntimeException("Description too long, must be under 3000 characters");
        }
    }
}
