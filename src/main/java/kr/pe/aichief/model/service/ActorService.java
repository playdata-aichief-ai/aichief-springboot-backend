package kr.pe.aichief.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.pe.aichief.model.dto.ActorDTO;
import kr.pe.aichief.model.dto.ClientDTO;
import kr.pe.aichief.model.dto.ManagerDTO;
import kr.pe.aichief.model.repository.AssignRepository;
import kr.pe.aichief.model.repository.ClientRepository;
import kr.pe.aichief.model.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ActorService {
	
	private final ClientRepository clientRepository;
	
	private final ManagerRepository managerRepository;
	
	private final AssignRepository assignRepository;
	
	public ActorDTO getActor(String email, String role) {
		
		ActorDTO result = null;
		
		if (role.equals("client")) {
			result = ClientDTO.toClientDTO(clientRepository.findByEmail(email));
		} else {
			result = ManagerDTO.toManagerDTO(managerRepository.findByEmail(email));
		}
		
		return result;
	}
	
	public List<ClientDTO> getClients(String email) {
		
		return assignRepository.findAssignByManager_Email(email)
				.stream().map(assign -> ClientDTO.toClientDTO(assign.getClient()))
				.collect(Collectors.toList());
	}
}
