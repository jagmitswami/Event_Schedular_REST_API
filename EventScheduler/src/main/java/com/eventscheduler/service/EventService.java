package com.eventscheduler.service;



import java.time.LocalDate;
import java.time.LocalTime;

import com.eventscheduler.exception.EventException;
import com.eventscheduler.exception.UserException;
import com.eventscheduler.model.Event;

public interface EventService {

	public Event createRecurringEvent(Event event, String type) throws UserException, EventException;
	
	public Event createNonRecurringEvent(Event event, String type) throws UserException, EventException;
	
	public Event updateEvent(Integer eventId, Event event) throws UserException, EventException;
	
	public String deleteEvent(Integer eventId) throws UserException, EventException;
	
	public boolean eventExist(LocalDate sdate, LocalTime stime, LocalDate edate, LocalTime etime);
	
}
