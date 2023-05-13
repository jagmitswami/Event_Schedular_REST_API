package com.eventscheduler.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = Access.READ_ONLY)
	private Integer userId;
	
	//@Pattern(regexp = "^((?![0-9])(?![!@#$%^&*]).)*")
	private String firstName;
	//@Pattern(regexp = "^((?![0-9])(?![!@#$%^&*]).)*")
	private String lastName;
	
	@Size(max = 10, min = 10, message = "Phone number should be of 10 digits")
	//@Column(unique = true, nullable = false)
	private String phone;
	
	@Email
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Size(max = 12, min = 6)
	//@Pattern(regexp = "^[A-Za-z0-9][!@#$%^&*]*")
	private String password;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent
	private LocalDate dob;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Event> events = new ArrayList<>();
	
}
