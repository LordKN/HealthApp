package com.HealthApp.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.HealthApp.model.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
	List<Certificate> findByIssOrg(String org); //This will find the field issOrg so name must match
	List<Certificate> findByDescriptionContainingIgnoreCase(String desc);
	List<Certificate> findByCerNameContainingIgnoreCase(String name);
}
