package com.eventscheduler.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eventscheduler.exception.EventException;
import com.eventscheduler.exception.UserException;
import com.eventscheduler.model.Customer;
import com.eventscheduler.model.Event;
import com.eventscheduler.model.EventType;
import com.eventscheduler.repository.EventRepo;
import com.eventscheduler.repository.UserRepo;

@Service
public class EventServiceImpl implements EventService{

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EventRepo eventRepo;
	
	@Override
	public Event createRecurringEvent(Event event, String type) throws UserException, EventException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = userRepo.findByPhone(phone).get();
		
		if(event.getStartDate().isAfter(event.getEndDate())) {
			throw new EventException("Invalid Event scheduling time");
		}
		
		if(eventExist(event.getStartDate(), event.getStartTime(), event.getEndDate(), event.getEndTime())) {
			throw new EventException("Event already planned for this timing");
		}
		
		event.setEventType(EventType.valueOf(type));
		
		event = eventRepo.save(event);
		
		customer.getEvents().add(event);
		
		userRepo.save(customer);
		
		return event;
		
	}

	@Override
	public Event createNonRecurringEvent(Event event, String type) throws UserException, EventException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = userRepo.findByPhone(phone).get();;
		
		if(event.getStartDate().isAfter(event.getEndDate())) {
			throw new EventException("Invalid Event scheduling time");
		}
		
		if(eventExist(event.getStartDate(), event.getStartTime(), event.getEndDate(), event.getEndTime())) {
			throw new EventException("Event already planned for this timing");
		}
		
		event.setEventType(EventType.valueOf(type));
		
		event = eventRepo.save(event);
		
		customer.getEvents().add(event);
		
		userRepo.save(customer);
		
		return event;
		
	}

	@Override
	public Event updateEvent(Integer eventId, Event event) throws UserException, EventException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = userRepo.findByPhone(phone).get();;
		
		if(event.getStartDate().isAfter(event.getEndDate())) {
			throw new EventException("Invalid Event scheduling time");
		}
		
		deleteEvent(eventId);
		
		if(eventExist(event.getStartDate(), event.getStartTime(), event.getEndDate(), event.getEndTime())) {
			throw new EventException("Event already planned for this timing");
		}
		
		event = eventRepo.save(event);
		
		customer.getEvents().add(event);
		userRepo.save(customer);
		
		return event;
		
	}

	@Override
	public String deleteEvent(Integer eventId) throws UserException, EventException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = userRepo.findByPhone(phone).get();;
		
		List<Event> allEvents = customer.getEvents();

		Event e = null;
		for(Event i: allEvents) {
			if(i.getEventId() == eventId) {
				e = i;
				allEvents.remove(i);
				break;
			}
		}
		
		if(e == null) throw new EventException("Please enter valid event id");
		
		customer.setEvents(allEvents);
		userRepo.save(customer);
		
		eventRepo.delete(e);
		
		return "Event Deleted successfully";
		
	}

	@Override
	public boolean eventExist(LocalDate sdate, LocalTime stime, LocalDate edate, LocalTime etime) {
		
		List<Event> startEvents = eventRepo.getByEndDateGreaterThanEqualAndStartDateLessThanEqual(sdate);
		List<Event> endEvents = eventRepo.getByEndDateGreaterThanEqualAndStartDateLessThanEqual(edate);
		
		for(Event e : startEvents) {
			if((stime.isAfter(e.getStartTime()) && stime.isBefore(e.getEndTime())) || (etime.isAfter(e.getStartTime()) && etime.isBefore(e.getEndTime())) || e.getStartTime().equals(stime) || e.getStartTime().equals(etime) || e.getEndTime().equals(stime) || e.getEndTime().equals(etime)) {
				return true;
			}
			if(stime.isBefore(e.getStartTime()) && etime.isAfter(e.getEndTime())) {
				return true;
			}
		}
		
		for(Event e : endEvents) {
			if((stime.isAfter(e.getStartTime()) && stime.isBefore(e.getEndTime())) || (etime.isAfter(e.getStartTime()) && etime.isBefore(e.getEndTime())) || e.getStartTime().equals(stime) || e.getStartTime().equals(etime) || e.getEndTime().equals(stime) || e.getEndTime().equals(etime)) {
				return true;
			}
			if(stime.isBefore(e.getStartTime()) && etime.isAfter(e.getEndTime())) {
				return true;
			}
		}
		
		return false;
	}

	

}
