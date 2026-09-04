package com.HealthApp.repo;

import java.util.List;
import java.util.Optional;

import com.HealthApp.model.Coach;
import com.HealthApp.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository  extends JpaRepository <Coach, Long> {
	List<Coach> findBySpecialty (Specialty specialty); //This will find the field speciality so name must match
	List<Coach> findByOpenForNewClientTrue();
	List<Coach> findByYearsOfExperienceBetween(int minYear, int maxYear);
	List<Coach> findByClientCountBetween (int minCount, int maxCount);
	List<Coach> findByWorkPlaceIgnoreCase (String workplace);
	List<Coach> findByDescriptionContainingIgnoreCase (String desc);
	List<Coach> findByNameContainingIgnoreCase (String name);
    Optional<Coach> findByEmail(String email);
}
