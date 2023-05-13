package com.eventscheduler.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eventscheduler.exception.EventException;
import com.eventscheduler.exception.UserException;
import com.eventscheduler.model.Customer;
import com.eventscheduler.model.Event;
import com.eventscheduler.repository.UserRepo;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	UserRepo userRepo;
	
	@Override
	public Customer registerUser(Customer customer) throws UserException {
		
		Customer existingUser = userRepo.findByPhone(customer.getPhone()).get();
		if(existingUser != null) throw new UserException("User already exist with this mobile number");
		
		return userRepo.save(customer);
		
	}

	@Override
	public List<Event> viewEvents(String type) throws EventException, UserException {
		
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = userRepo.findByPhone(phone).get();
		
		List<Event> allEvents = customer.getEvents();
		
		List<Event> events = new ArrayList<>();
		
		if(type.equalsIgnoreCase("month")) {
			
			for(Event e: allEvents) {
				
				if(e.getStartDate().getMonth().equals(LocalDate.now().getMonth())) {
					events.add(e);
				}else if(e.getEndDate().getMonth().equals(LocalDate.now().getMonth())) {
					events.add(e);
				}
				
			}
			
			
		}else if (type.equalsIgnoreCase("week")) {
			
			for(Event e: allEvents) {
				
				if(e.getStartDate().isBefore(LocalDate.now().plusDays(7)) && (e.getStartDate().isAfter(LocalDate.now()) || e.getStartDate().isEqual(LocalDate.now()))) {
					events.add(e);
				}else if(e.getEndDate().isBefore(LocalDate.now().plusDays(7)) && (e.getEndDate().isAfter(LocalDate.now()) || e.getEndDate().isEqual(LocalDate.now()))) {
					events.add(e);
				}
				
			}
			
		}else if (type.equalsIgnoreCase("day")) {
			
			for(Event e: allEvents) {
				
				if(e.getStartDate().isEqual(LocalDate.now())) {
					events.add(e);
				}else if(e.getEndDate().isEqual(LocalDate.now())) {
					events.add(e);
				}
				
			}
			
			
		}
		
		if(events.isEmpty()) throw new EventException("No events found");
		
		return events;
		
	}

	@Override
	public Customer updateUser(Customer customer) throws UserException {
		
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer existingUser = userRepo.findByPhone(phone).get();;
		
		if(customer.getDob() != null) existingUser.setDob(customer.getDob());
		if(customer.getFirstName() != null) existingUser.setFirstName(customer.getFirstName());
		if(customer.getLastName() != null) existingUser.setLastName(customer.getLastName());
		
		return userRepo.save(existingUser);
		
	}

}
