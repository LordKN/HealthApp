package com.HealthApp.service;

import java.util.List;

import com.HealthApp.model.Certificate;
import com.HealthApp.model.Specialty;
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

    public Coach findCoachByEmail (String email) {
        Coach coach = repo.findByEmail(email);
        if (coach == null) {
            throw new RuntimeException("Coach not found");
        }
        return coach;
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

        if (coach.getRole() == null) {
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
        existingCoach.setPassword(updatedCoach.getPassword());

        validateCoach(existingCoach);
        return repo.save(existingCoach);
    }


    /*
    *When a new Certificate is received from the front end,
    * its id is initially null because it has not been saved yet
    *
    * Hibernate generates the primary key automatically when
    * repo.save(coach) is executed (via CascadeType.ALL),
    * then updates the Certificate object with a generated id.
     */

    public Coach addCertificate(Long coachID, Certificate certificate) {
        Coach existingCoach = repo.findById(coachID).orElseThrow(() -> new RuntimeException("Coach not found"));

        existingCoach.addCertificate(certificate);
        repo.save(existingCoach);
        return existingCoach;
    }

    public void removeCertificate (Long coachID, Long certificateId) {
        Coach existingCoach = repo.findById(coachID).orElseThrow(() -> new RuntimeException("Coach not found"));

        /*
         * Search through the coach's certificate list to find the certificate
         * whose ID matches the one passed into the API.
         *
         * stream()            -> Creates a stream to process each certificate. This works like a for loop looping through the certificate list
         * filter(...)         -> Keeps only certificates whose ID matches.
         * findFirst()         -> Returns the first matching certificate.
         * orElseThrow(...)    -> If no certificate is found, throw an exception.
         */
        Certificate certToRemove = existingCoach.getCertifications()
                .stream()
                .filter(cert -> cert.getId().equals(certificateId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Certification found"));

        existingCoach.removeCertificate(certToRemove);

        repo.save(existingCoach);
    }

    public List<Coach> getCoachBySpecialty(Specialty specialty) {
        return  repo.findBySpecialty(specialty);
    }

    public List<Coach> getCoachByOpenForNewClient() {
        return repo.findByOpenForNewClientTrue();
    }

    public List<Coach> getCoachByYearsOfExperienceBetween(int minYear, int maxYear) {
        validateRange(minYear, maxYear, "Years of Experience ");
        return repo.findByYearsOfExperienceBetween(minYear, maxYear);
    }

    public List<Coach> getByClientCountBetween (int minCount, int maxCount) {
        validateRange(minCount, maxCount, "Client count ");
        return repo.findByClientCountBetween(minCount, maxCount);
    }

    public List<Coach> getByWorkplace(String workplace) {
        return repo.findByWorkPlaceIgnoreCase(workplace);
    }

    public List<Coach> getByDescription(String desc) {
        return repo.findByDescriptionContainingIgnoreCase(desc);
    }

    public List<Coach> getByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    private void validateRange (Integer min, Integer max, String fieldName) {
        if (min == null || max == null) {
            throw new RuntimeException(fieldName + " min and max are required");
        }

        if (min > max) {
            throw new RuntimeException((fieldName + " min cannot be greater than max"));
        }
    }
}
