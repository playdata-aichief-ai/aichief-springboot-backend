package kr.pe.aichief.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.aichief.model.dto.InsuredDTO;
import kr.pe.aichief.model.service.InsuredService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/insured")
@RequiredArgsConstructor
public class InsuredController {
	
	private final InsuredService insuredService;
	
	@GetMapping
	public ResponseEntity<InsuredDTO> getInsured(@RequestParam("email") String email) {
		
		InsuredDTO result = insuredService.getInsured(InsuredDTO.builder().email(email).build());
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
