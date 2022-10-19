package kr.pe.aichief.model.service;

import org.springframework.stereotype.Service;

import kr.pe.aichief.model.dto.ManagerDTO;
import kr.pe.aichief.model.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ManagerService {

	private final ManagerRepository managerRepository;
	
	public ManagerDTO getManager(String name, String email) {
		return ManagerDTO.toManagerDTO(managerRepository.findByNameAndEmail(name, email));
	}
}
