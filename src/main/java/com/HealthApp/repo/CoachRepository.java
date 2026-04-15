package com.HealthApp.repo;

import java.util.List;
import com.HealthApp.model.Coach;
import com.HealthApp.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository  extends JpaRepository <Coach, Long> {
	List<Coach> findBySpecialty (Specialty specialty); //This will find the field speciality so name must match
	List<Coach> findByOpenForNewClientTrue();
}
