package kr.pe.aichief.model.service;

import org.springframework.stereotype.Service;

import kr.pe.aichief.model.dto.ClientDTO;
import kr.pe.aichief.model.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientService {
	
	private final ClientRepository clientRepository;
	
	public ClientDTO getClient(String name, String email) {
		return ClientDTO.toClientDTO(clientRepository.findByNameAndEmail(name, email));
	}
}
