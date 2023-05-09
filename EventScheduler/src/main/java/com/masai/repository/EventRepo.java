package com.masai.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.masai.model.Event;

public interface EventRepo extends JpaRepository<Event, Integer>{

	List<Event> findByStartDate(LocalDate startDate);
	
	@Query("select e from Event e where e.startDate <= ?1 and e.endDate >= ?1")
	List<Event> getByEndDateGreaterThanEqualAndStartDateLessThanEqual(LocalDate startDate);
	
}
