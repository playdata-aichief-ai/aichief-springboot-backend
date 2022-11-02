package kr.pe.aichief.model.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString

public class MyResponse {
	
	private Integer code;
	
	private HttpStatus httpStatus;
	
	private String message;
	
	private Integer count;
	
	private List<Object> contents;
}
