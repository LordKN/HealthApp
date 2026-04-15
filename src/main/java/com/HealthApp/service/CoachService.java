package com.HealthApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.HealthApp.model.Coach;
import com.HealthApp.model.Specialty;
import com.HealthApp.repo.CoachRepository;

@Service
public class CoachService {

    @Autowired
    private CoachRepository repo;

    public List<Coach> findAllCoaches() {
        return repo.findAll();
    }

    public Coach findCoachById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Coach not found"));
    }

    public void saveCoach (Coach coach) {
        repo.save(coach);
    }

    public void deleteCoach(Long id) {
        repo.deleteById(id);
    }

    public long countCoaches() {
        return repo.count();
    }

    public void deleteAllCoaches() {
        repo.deleteAll();
    }
}
