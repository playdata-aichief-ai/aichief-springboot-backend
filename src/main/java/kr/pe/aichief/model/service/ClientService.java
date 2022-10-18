package kr.pe.aichief.model.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import kr.pe.aichief.model.dto.ClientDTO;
import kr.pe.aichief.model.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientService {
	
	private final ClientRepository clientRepository;
	
	public ClientDTO findByPhoneNumber(String phoneNumber) {
		return ClientDTO.toClientDTO(clientRepository.findById(phoneNumber).orElseThrow(() -> new EntityNotFoundException("Client Not Found: " + phoneNumber)));
	}
}
