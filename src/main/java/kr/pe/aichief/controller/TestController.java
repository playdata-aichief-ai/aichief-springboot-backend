package kr.pe.aichief.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.aichief.model.dto.MyResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
	
	@GetMapping
	public ResponseEntity<MyResponse> requestTest(@RequestParam("email") String email) {
		
		MyResponse myResponse = new MyResponse();
		
		myResponse.setCode(HttpStatus.OK.value());
		myResponse.setHttpStatus(HttpStatus.OK);
		myResponse.setMessage("test success: " + email);
		myResponse.setCount(0);
		myResponse.setContents(new ArrayList<Object>());
		
		return ResponseEntity.status(myResponse.getHttpStatus()).body(myResponse);
	}
}
