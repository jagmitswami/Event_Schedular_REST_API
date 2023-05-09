package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.EventException;
import com.masai.exception.UserException;
import com.masai.model.Event;
import com.masai.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventscheduler/event")
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@PostMapping("/createRecurringEvent")
	public ResponseEntity<Event> createRecurring(@Valid @RequestBody Event event) throws UserException, EventException{
		
		event = eventService.createRecurringEvent(event, "Recurring");
		return new ResponseEntity<>(event, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/createNonRecurringEvent")
	public ResponseEntity<Event> createNonRecurring(@Valid @RequestBody Event event) throws UserException, EventException{
		
		Event res = eventService.createNonRecurringEvent(event, "Non_recurring");
		return new ResponseEntity<>(res, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/updateEvent/{eventId}")
	public ResponseEntity<Event> updateEvent(@Valid @RequestBody Event event, @PathVariable("eventId") Integer eventId) throws UserException, EventException{
		
		Event res = eventService.updateEvent(eventId, event);
		return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	
	@PutMapping("/deleteEvent/{eventId}")
	public ResponseEntity<String> deleteEvent(@PathVariable("eventId") Integer eventId) throws UserException, EventException{
		
		String res = eventService.deleteEvent(eventId);
		return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	
}
