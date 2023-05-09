package com.masai.service;



import java.time.LocalDate;
import java.time.LocalTime;

import com.masai.exception.EventException;
import com.masai.exception.UserException;
import com.masai.model.Event;

public interface EventService {

	public Event createRecurringEvent(Event event, String type) throws UserException, EventException;
	
	public Event createNonRecurringEvent(Event event, String type) throws UserException, EventException;
	
	public Event updateEvent(Integer eventId, Event event) throws UserException, EventException;
	
	public String deleteEvent(Integer eventId) throws UserException, EventException;
	
	public boolean eventExist(LocalDate sdate, LocalTime stime, LocalDate edate, LocalTime etime);
	
}
