package kr.pe.aichief.exceptions;

import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	}
}
