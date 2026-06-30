package com.HealthApp.controller;

import com.HealthApp.model.Certificate;
import com.HealthApp.model.Coach;
import com.HealthApp.model.Specialty;
import com.HealthApp.service.CertificateService;
import com.HealthApp.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoachController {

    @Autowired
    private CoachService service;

    @Autowired
    private CertificateService certificateService;

    @GetMapping("/api/coaches")
    public List<Coach> getAllCoaches() {
        return service.findAllCoaches();
    }

    @GetMapping("/api/coaches/{coachId}")
    public Coach getCoach(@PathVariable("coachId") Long id) {
        return service.findCoachById(id);
    }

    @GetMapping("/api/coaches/count")
    public Long countCoaches() {
        return service.countCoaches();
    }

    @PostMapping("/api/coaches")
    public ResponseEntity<Coach> saveCoach(@RequestBody Coach coach) {

        Coach savedCoach = service.saveCoach(coach);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCoach);
    }

    @PutMapping("/api/coaches/{id}")
    public ResponseEntity<Coach> updateCoach(@PathVariable Long id, @RequestBody Coach updatedCoach) {
        Coach newCoach = service.updateCoach(id, updatedCoach);
        return ResponseEntity.ok(newCoach);
    }

    @DeleteMapping("/api/coaches/{coachID}")
    public ResponseEntity<Void> deleteCoach(@PathVariable("coachID") Long id) {
        service.deleteCoach(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/coaches")
    public ResponseEntity<Void> deleteAllCoaches() {
        service.deleteAllCoaches();
        return ResponseEntity.noContent().build();
    }

    /*
    *Certificate belongs to a coach, so it should be nested under coach
     */

    @PostMapping("/api/coaches/{coachID}/certificates")
    public ResponseEntity<Coach> addCertificate(@PathVariable Long coachID, @RequestBody Certificate certificate) {
        Coach updatedCoach = service.addCertificate(coachID, certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCoach);
    }

    @DeleteMapping("/api/coaches/{coachID}/certificates/{certificateId}")
    public ResponseEntity<Void> removeCertificate(@PathVariable Long coachID, @PathVariable Long certificateId) {
        service.removeCertificate(coachID, certificateId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/coaches/{coachID}/certificates")
    public List<Certificate> getAllCertificate(@PathVariable Long coachID) {
        Coach existingCoach = service.findCoachById(coachID);
        return existingCoach.getCertifications();
    }

    @GetMapping("/api/coaches/specialties/{specialty}")
    public List<Coach> getCoachBySpecialty(@PathVariable Specialty specialty) {
        return service.getCoachBySpecialty(specialty);
    }

    @GetMapping("/api/coaches/availabilities")
    public List<Coach> getCoachByAvailability() {
        return service.getCoachByOpenForNewClient();
    }

    @GetMapping("/api/coaches/yearsOfExperience")
    public List<Coach> getCoachByYearsOfExperience(@RequestParam int minYear, @RequestParam int maxYear) {
        return service.getCoachByYearsOfExperienceBetween(minYear, maxYear);
    }

    @GetMapping("/api/coaches/clientCounts")
    public List<Coach> getCoachByCountBetween(@RequestParam int minCount, @RequestParam int maxCount) {
        return service.getByClientCountBetween(minCount, maxCount);
    }

    @GetMapping("/api/coaches/workplaces")
    public List<Coach> getCoachByWorkplace (String workplace) {
        return service.getByWorkplace(workplace);
    }

    @GetMapping("/api/coaches/descriptions")
    public List<Coach> getCoachByDescription(String desc) {
        return service.getByDescription(desc);
    }

    @GetMapping("/api/coaches/names")
    public List<Coach> getCoachByName(String name) {
        return service.getByName(name);
    }
}
