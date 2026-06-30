package com.HealthApp.controller;

import com.HealthApp.model.Certificate;
import com.HealthApp.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CertificateController {

    @Autowired
    private CertificateService service;

    @GetMapping("/api/certificates")
    public List<Certificate> getAllCertificate() {
        return service.getAllCertificates();
    }

    @GetMapping("/api/certificates/{cerID}")
    public Certificate getCertificate(@PathVariable Long cerID) {
        return service.getCertificateById(cerID);
    }

    @GetMapping("/api/certificates/count")
    public Long countCertificate() {
        return service.countCertificate();
    }

    @DeleteMapping("/api/certificates/{cerID}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long cerID) {
        service.deleteCertificate(cerID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/certificates")
    public ResponseEntity<Void> deleteAllCertificate() {
        service.deleteAllCertificate();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/certificates")
    public ResponseEntity<Certificate> saveCertificate(@RequestBody Certificate certificate) {
        Certificate savedCertificate = service.saveCertificate(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCertificate);
    }

    @GetMapping("/api/certificates/orgs/{org}")
    public List<Certificate> getCertificateByOrganization(@PathVariable String org) {
        return service.getCertificateByIssOrg(org);
    }

    @GetMapping("/api/certifications/descriptions/{desc}")
    public List<Certificate> getCertificateByDescription(@PathVariable String desc) {
        return service.getCertificateByDescription(desc);
    }
    @GetMapping("/api/certifications/names/{name}")
    public List<Certificate> getCertificateByName(@PathVariable String name) {
        return service.getCertificateByName(name);
    }
}
