package kr.pe.aichief.model.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import kr.pe.aichief.model.dto.InsuredDTO;
import kr.pe.aichief.model.repository.InsuredRepository;
import kr.pe.aichief.util.DTOConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuredService {
	
	private final DTOConverter dtoConverter;
	
	private final InsuredRepository insuredRepository;
	
	public InsuredDTO getInsured(InsuredDTO insuredDTO) {
		
		return dtoConverter.insuredToDTO(
				insuredRepository.findByEmail(insuredDTO.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("Insured Not Found: " + insuredDTO.getEmail())));
	}
}
