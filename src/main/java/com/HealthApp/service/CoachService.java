package com.HealthApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.HealthApp.model.Coach;
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

    public Coach saveCoach (Coach coach) {

        validateCoach(coach);

        //Important: keep both sides of Coach-Certificate relationship synced
        if (coach.getCertifications() != null) {
            coach.getCertifications().forEach(certificate -> certificate.setCoach(coach));
        }
        repo.save(coach);
        return coach;
    }

    public void deleteCoach(Long id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Coach doesn't exist for deletion");
        }

        repo.deleteById(id);
    }

    public long countCoaches() {
        return repo.count();
    }

    public void deleteAllCoaches() {

        if (repo.count() == 0) {
            throw new RuntimeException("No coach to delete");
        }

        repo.deleteAll();
    }

    private void validateCoach(Coach coach) {
        if (coach == null) {
            throw new RuntimeException("Coach cannot be null");
        }

        if (coach.getName() == null || coach.getName().isBlank()) {
            throw new RuntimeException("Coach name is required");
        }

        if (coach.getEmail() == null || coach.getEmail().isBlank()) {
            throw new RuntimeException("Coach email is required");
        }

        if (!coach.getEmail().contains("@")) {
            throw new RuntimeException("Coach email must be valid");
        }

        if (coach.getSpecialty() == null) {
            throw new RuntimeException("Coach specialty is required");
        }

        if (coach.getYearsOfExperience() < 0) {
            throw new RuntimeException("Years of experience cannot be negative");
        }

        if (coach.getClientCount() > 20) {
            throw new RuntimeException("Client count cannot be greater than 20");
        }

        if (coach.getWorkPlace() == null || coach.getWorkPlace().isBlank()) {
            throw new RuntimeException("Workplace is required");
        }

        if (coach.getDesc() != null && coach.getDesc().length() > 1000) {
            throw new RuntimeException("Description must be under 1000 characters");
        }

        if (coach.getRole() != null) {
            throw new RuntimeException("Role must be assigned");
        }
    }

    public Coach updateCoach(Long id, Coach updatedCoach) {
        Coach existingCoach = repo.findById(id).orElseThrow(() -> new RuntimeException("No Coach found"));

        existingCoach.setSpecialty(updatedCoach.getSpecialty());
        existingCoach.setYearsOfExperience(updatedCoach.getYearsOfExperience());
        existingCoach.setOpenForNewClient(updatedCoach.isOpenForNewClient());
        existingCoach.setClientCount(updatedCoach.getClientCount());
        existingCoach.setWorkPlace(updatedCoach.getWorkPlace());
        existingCoach.setDesc(updatedCoach.getDesc());

        validateCoach(existingCoach);
        return repo.save(existingCoach);
    }
}
