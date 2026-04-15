package com.HealthApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.HealthApp.model.MedicalHistory;


public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long>{
	List<MedicalHistory> findByClient_Id(Long clientId); //This will find the column client_id so name must match
}
