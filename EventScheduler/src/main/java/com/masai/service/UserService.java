package com.masai.service;

import java.util.List;

import com.masai.exception.EventException;
import com.masai.exception.UserException;
import com.masai.model.Event;
import com.masai.model.Customer;

public interface UserService {

	public Customer registerUser(Customer customer) throws UserException;
	
	public List<Event> viewEvents(String type) throws EventException, UserException;
	
	public Customer updateUser(Customer customer) throws UserException;
	
}
