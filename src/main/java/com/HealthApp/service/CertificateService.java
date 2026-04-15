package com.HealthApp.service;

import com.HealthApp.model.Certificate;
import com.HealthApp.repo.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository repo;

    public List<Certificate> getAllCertificates() {
        return repo.findAll();
    }

    public Certificate getCertificateById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
    }
}

//findById(id) will return an Optional object to represent a value that may or not may present,
//  forcing developers to explicitly handle the absence of a value.
