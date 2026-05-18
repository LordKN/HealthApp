package com.HealthApp.controller;

import com.HealthApp.model.Certificate;
import com.HealthApp.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/api/certificate/{cerID}")
    public Certificate getCertificate(@PathVariable("cerID") Long cerID) {
        return service.getCertificateById(cerID);
    }

    @GetMapping("/api/certificates/count")
    public Long countCertificate() {
        return service.countCertificate();
    }

    @DeleteMapping("/api/certificate/{cerID}")
    public String deleteCertificate(@PathVariable("cerID") Long cerID) {
        service.deleteCertificate(cerID);
        return "Deleted";
    }

    @DeleteMapping("/api/certificates")
    public String deleteAllCertificate() {
        service.deleteAllCertificate();
        return "Deleted all certificate";
    }

    @PostMapping("/api/certificate")
    public void saveCertificate(@RequestBody Certificate certificate) {
        service.saveCertificate(certificate);
    }
}
