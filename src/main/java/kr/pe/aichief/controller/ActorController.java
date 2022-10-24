package kr.pe.aichief.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.aichief.model.dto.ActorDTO;
import kr.pe.aichief.model.dto.ClientDTO;
import kr.pe.aichief.model.service.ActorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class ActorController {
	
	private final ActorService actorService;
	
	@GetMapping("/my")
	public ActorDTO getMyInfo(@RequestParam("email") String email, @RequestParam("role") String role) {
		return actorService.getActor(email, role);
	}
	
	@GetMapping("/clients")
	public List<ClientDTO> getClientsInfo(@RequestParam("email") String email) {
		return actorService.getClients(email);
	}
}
