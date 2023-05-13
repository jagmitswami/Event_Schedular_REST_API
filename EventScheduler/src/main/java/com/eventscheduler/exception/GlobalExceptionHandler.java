package com.eventscheduler.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetails> wrongUriException(NoHandlerFoundException e, WebRequest req){
		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> validationException(MethodArgumentNotValidException e){
		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getBindingResult().getFieldError().getDefaultMessage(), "Validation Error");
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> anyOtherException(Exception e, WebRequest req){
		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<MyErrorDetails> userException(UserException e, WebRequest req){
		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<MyErrorDetails> loginException(LoginException e, WebRequest req){
		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(EventException.class)
	public ResponseEntity<MyErrorDetails> eventException(EventException e, WebRequest req){
		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<MyErrorDetails> dataException(EventException e, WebRequest req){
		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.BAD_GATEWAY);
	}
}
