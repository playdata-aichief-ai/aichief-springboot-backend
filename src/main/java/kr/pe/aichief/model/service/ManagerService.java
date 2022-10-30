package kr.pe.aichief.model.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import kr.pe.aichief.model.dto.ManagerDTO;
import kr.pe.aichief.model.repository.ManagerRepository;
import kr.pe.aichief.util.DTOConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerService {
	
	private final DTOConverter dtoConverter;
	
	private final ManagerRepository managerRepository;
	
	public ManagerDTO getManager(ManagerDTO managerDTO) {
		
		return dtoConverter.managerToDTO(managerRepository
				.findByEmail(managerDTO.getEmail())
				.orElseThrow(() -> new EntityNotFoundException()));
	}
}
