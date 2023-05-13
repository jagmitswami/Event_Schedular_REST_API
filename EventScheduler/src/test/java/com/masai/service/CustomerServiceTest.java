package com.masai.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.eventscheduler.exception.EventException;
import com.eventscheduler.exception.UserException;
import com.eventscheduler.model.Customer;
import com.eventscheduler.model.Event;
import com.eventscheduler.repository.UserRepo;
import com.eventscheduler.service.CustomerServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CustomerServiceTest {
	
	@Mock
	private UserRepo userRepo;
	
	@Mock
	private SecurityContext securityContext;
	
	@Mock
	private Authentication authentication;
	
	@InjectMocks
	private CustomerServiceImpl customerService;
	
	private Optional<Customer> opt;
	
	@Test
	@Order(1)
	public void testRegisterUser() throws UserException {
		
		List<Event> al = new ArrayList<>();
		opt = Optional.of((Customer) new Customer(1, "fn", "ln", "9999999999", "fn@gmail.com", "pass", LocalDate.parse("01-01-2001", DateTimeFormatter.ofPattern("dd-MM-yyy")), al));
		
		when(userRepo.findByPhone(anyString())).thenReturn(opt);
		
		assertAll(() -> {
			assertThrows(UserException.class, ()-> customerService.registerUser(opt.get()));
		});
		
		verify(userRepo, never()).save(any());
		
	}
	
	@Test
	@Order(2)
	public void testViewEvents() throws UserException {
		
		List<Event> al = new ArrayList<>();
		opt = Optional.of((Customer) new Customer(1, "fn", "ln", "9999999999", "fn@gmail.com", "pass", LocalDate.parse("01-01-2001", DateTimeFormatter.ofPattern("dd-MM-yyy")), al));
		
		when(userRepo.findByPhone(anyString())).thenReturn(opt);
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("9999999999");
		
		assertAll(() -> {
			assertThrows(EventException.class, ()-> customerService.viewEvents("month"));
		});
		
	}
	
	@Test
	@Order(3)
	public void testUpdateUser() throws UserException {
		
		List<Event> al = new ArrayList<>();
		opt = Optional.of((Customer) new Customer(1, "fn", "ln", "9999999999", "fn@gmail.com", "pass", LocalDate.parse("01-01-2001", DateTimeFormatter.ofPattern("dd-MM-yyy")), al));
		
		when(userRepo.findByPhone(anyString())).thenReturn(opt);
		when(userRepo.save(any())).thenReturn(opt.get());
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("9999999999");
		
		Customer customer = customerService.updateUser(opt.get());
		
		assertAll(() -> {
			assertEquals(1, customer.getUserId());
		});
		
	}
	
}
