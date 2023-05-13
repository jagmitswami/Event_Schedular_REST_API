package com.masai.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
import com.eventscheduler.model.EventType;
import com.eventscheduler.repository.EventRepo;
import com.eventscheduler.repository.UserRepo;
import com.eventscheduler.service.EventServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class EventServiceTest {
	
	@Mock
	private EventRepo eventRepo;
	
	@Mock
	private UserRepo userRepo;
	
	@Mock
	private SecurityContext securityContext;
	
	@Mock
	private Authentication authentication;
	
	@InjectMocks
	private EventServiceImpl eventService;
	
	private Event event;
	private Optional<Customer> opt;
	
	@BeforeEach
	public void init() {
		
		event = new Event(101, EventType.valueOf("Recurring"), LocalDate.parse("15-05-2023", DateTimeFormatter.ofPattern("dd-MM-yyy")), LocalDate.parse("15-05-2023", DateTimeFormatter.ofPattern("dd-MM-yyy")), LocalTime.parse("09:00:00"), LocalTime.parse("15:00:00"));
		List<Event> al = new ArrayList<>();
		al.add(event);
		opt = Optional.of((Customer) new Customer(1, "fn", "ln", "9999999999", "fn@gmail.com", "pass", LocalDate.parse("01-01-2001", DateTimeFormatter.ofPattern("dd-MM-yyy")), al));
		
		when(eventRepo.save(any())).thenReturn(event);
		when(eventRepo.getByEndDateGreaterThanEqualAndStartDateLessThanEqual(any())).thenReturn(new ArrayList<>());
		when(userRepo.findByPhone(anyString())).thenReturn(opt);
		when(userRepo.save(any())).thenReturn(opt.get());
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("9999999999");
		
	}
	
	@Test
	@Order(1)
	public void testCreateRecurringEvent() throws UserException, EventException {
		
		Event resEvent = eventService.createRecurringEvent(event, "Recurring");
		
		assertAll(() -> {
			assertEquals(101, resEvent.getEventId());
			
			assertEquals("Recurring", event.getEventType().toString());
		});
		
	}
	
	@Test
	@Order(2)
	public void testCreateNonRecurringEvent() throws UserException, EventException {
		
		Event resEvent = eventService.createNonRecurringEvent(event, "Non_recurring");
		
		assertAll(() -> {
			assertEquals(101, resEvent.getEventId());
			
			assertEquals("Non_recurring", event.getEventType().toString());
		});
		
	}
	
	@Test
	@Order(3)
	public void testDeleteEvent() throws UserException, EventException {
		
		eventService.createNonRecurringEvent(event, "Non_recurring");
		
		doNothing().when(eventRepo).delete(any());
		
		String res = eventService.deleteEvent(101);
		
		assertAll(() -> {
			assertEquals("Event Deleted successfully", res);
		});
		
	}
	
	@Test
	@Order(4)
	public void testDeleteEvent2() throws UserException, EventException {
		
		eventService.createNonRecurringEvent(event, "Non_recurring");
		
		assertAll(() -> {
			assertThrows(EventException.class, ()-> eventService.deleteEvent(1));
		});
		
	}
	
	@Test
	@Order(5)
	public void testUpdateEvent() throws UserException, EventException {
		
		eventService.createNonRecurringEvent(event, "Non_recurring");
		
		doNothing().when(eventRepo).delete(any());
		
		Event res = eventService.updateEvent(101, event);
		
		assertAll(() -> {
			assertEquals(101, res.getEventId());
		});
		
	}
	
	@Test
	@Order(6)
	public void testUpdateEvent2() throws UserException, EventException {
		
		eventService.createNonRecurringEvent(event, "Non_recurring");
		
		assertAll(() -> {
			assertThrows(EventException.class, ()-> eventService.updateEvent(1, event));
		});
		
	}
	
}
