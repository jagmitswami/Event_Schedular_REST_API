package com.eventscheduler.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = Access.READ_ONLY)
	private Integer eventId;
	
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
	private LocalDate startDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
	private LocalDate endDate;

	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime startTime;

	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime endTime;
	
}
