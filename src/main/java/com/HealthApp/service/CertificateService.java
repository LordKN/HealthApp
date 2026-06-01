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

        validateCertificate(certificate);
        repo.save(certificate);
    }

    public void deleteCertificate(Long id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Certificate not exist for deletion");
        }

        repo.deleteById(id);
    }

    public long countCertificate() {
        return repo.count();
    }

    public void deleteAllCertificate() {
        if (repo.count() == 0) {
            throw new RuntimeException("No certificate to delete");
        }

        repo.deleteAll();
    }

    private void validateCertificate(Certificate certificate) {
        if (certificate == null) {
            throw new RuntimeException("Certificate cannot be null");
        }

        if (certificate.getCerName() == null || certificate.getCerName().isBlank()) {
            throw new RuntimeException("Certificate is required");
        }

        if (certificate.getIssOrg() == null || certificate.getIssOrg().isBlank()) {
            throw new RuntimeException("Issuing organization is required");
        }

        if (certificate.getCerName().length() > 100) {
            throw new RuntimeException("Certificate name must be under 100 characters");
        }

        if (certificate.getIssOrg().length() > 100) {
            throw new RuntimeException("Issuing organization must be under 100 characters");
        }
    }
}
