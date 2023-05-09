package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.EventException;
import com.masai.exception.LoginException;
import com.masai.exception.UserException;
import com.masai.model.Event;
import com.masai.model.Customer;
import com.masai.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventscheduler/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Customer> register(@Valid @RequestBody Customer u) throws UserException{

		u = userService.registerUser(u);
		return new ResponseEntity<>(u, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/month_events")
	public ResponseEntity<List<Event>> viewByMonth(@PathVariable("key") String key) throws UserException, LoginException, EventException{
		
		List<Event> events = userService.viewEvents("month");
		return new ResponseEntity<>(events, HttpStatus.OK);
		
	}
	
	@GetMapping("/week_events")
	public ResponseEntity<List<Event>> viewByWeek(@PathVariable("key") String key) throws UserException, LoginException, EventException{
		
		List<Event> events = userService.viewEvents("week");
		return new ResponseEntity<>(events, HttpStatus.OK);
		
	}
	
	@GetMapping("/day_events")
	public ResponseEntity<List<Event>> viewByDay() throws UserException, LoginException, EventException{
		
		List<Event> events = userService.viewEvents("day");
		return new ResponseEntity<>(events, HttpStatus.OK);
		
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<Customer> viewByMonth(@Valid @RequestBody Customer u) throws UserException, LoginException, EventException{
		
		Customer customer = userService.updateUser(u);
		return new ResponseEntity<>(customer, HttpStatus.OK);
		
	}
}
