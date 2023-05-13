package com.eventscheduler.service;

import java.util.List;

import com.eventscheduler.exception.EventException;
import com.eventscheduler.exception.UserException;
import com.eventscheduler.model.Customer;
import com.eventscheduler.model.Event;

public interface CustomerService {

	public Customer registerUser(Customer customer) throws UserException;
	
	public List<Event> viewEvents(String type) throws EventException, UserException;
	
	public Customer updateUser(Customer customer) throws UserException;
	
}
