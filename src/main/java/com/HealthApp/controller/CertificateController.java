package com.HealthApp.controller;

import com.HealthApp.model.Certificate;
import com.HealthApp.repo.CertificateRepository;
import com.HealthApp.service.CertificateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CertificateController {

    private CertificateService service;

    @GetMapping("/api/certificates")
    public List<Certificate> getAllCertificate() {
        return service.getAllCertificates();
    }
}
