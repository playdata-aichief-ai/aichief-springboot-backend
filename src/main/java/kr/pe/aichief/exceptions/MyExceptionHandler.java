package kr.pe.aichief.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class MyExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> entityNotFoundHandler(Exception e) {
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
	}
}
