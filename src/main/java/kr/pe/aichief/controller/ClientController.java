package kr.pe.aichief.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.aichief.model.dto.ClientDTO;
import kr.pe.aichief.model.service.ClientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
	
	private final ClientService clientService;
	
	@GetMapping
	public ClientDTO searchClientInfo(@RequestParam("phone_number") String phoneNumber) throws Exception {
		return clientService.findByPhoneNumber(phoneNumber);
	}
}
