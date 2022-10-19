package kr.pe.aichief.controller;

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
	public ManagerDTO getManagerInfo(@RequestParam("name") String name, @RequestParam("email") String email) {
		return managerService.getManager(name, email);
	}
}
