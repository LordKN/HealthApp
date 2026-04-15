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

    public void saveCertificate(Certificate certificate) {
        repo.save(certificate);
    }

    public void deleteCertificate(Long id) {
        repo.deleteById(id);
    }

    public long countCertificate() {
        return repo.count();
    }

    public void deleteAllCertificate() {
        repo.deleteAll();
    }
}
