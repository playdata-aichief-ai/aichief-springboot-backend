package kr.pe.aichief.exceptions;

import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import kr.pe.aichief.model.dto.MyResponse;

@RestControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<MyResponse> entityNotFoundHandler(Exception e) {
		
		MyResponse result = MyResponse.builder()
				.code(HttpStatus.NOT_FOUND.value())
				.httpStatus(HttpStatus.NOT_FOUND)
				.count(0)
				.message(e.getMessage())
				.contents(new ArrayList<Object>())
				.build();
		
		return ResponseEntity.status(result.getHttpStatus()).body(result);
	}
	
	@ExceptionHandler({JsonMappingException.class, JsonProcessingException.class})
	public ResponseEntity<MyResponse> jsonParseExceptionHadler(Exception e) {
		
		MyResponse result = MyResponse.builder()
				.code(HttpStatus.SERVICE_UNAVAILABLE.value())
				.httpStatus(HttpStatus.SERVICE_UNAVAILABLE)
				.count(0)
				.message(e.getMessage())
				.contents(new ArrayList<Object>())
				.build();
		
		return ResponseEntity.status(result.getHttpStatus()).body(result);
	}
}
