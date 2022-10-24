package kr.pe.aichief.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthController {
	
	@GetMapping
	public ResponseEntity<HttpStatus> healthCheck() {
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
}
