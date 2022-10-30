package kr.pe.aichief.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.aichief.model.dto.ManagerDTO;
import kr.pe.aichief.model.service.ManagerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
	
	private final ManagerService managerService;
	
	@GetMapping
	public ResponseEntity<ManagerDTO> getManager(@RequestParam("email") String email) {
		
		ManagerDTO managerDTO = ManagerDTO.builder().email(email).build();
		ManagerDTO result = managerService.getManager(managerDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
