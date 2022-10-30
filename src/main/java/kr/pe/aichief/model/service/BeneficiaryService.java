package kr.pe.aichief.model.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import kr.pe.aichief.model.dto.BeneficiaryDTO;
import kr.pe.aichief.model.dto.ManagerDTO;
import kr.pe.aichief.model.repository.AssignRepository;
import kr.pe.aichief.model.repository.BeneficiaryRepository;
import kr.pe.aichief.util.DTOConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeneficiaryService {
	
	private final DTOConverter dtoConverter;

	private final BeneficiaryRepository beneficiaryRepository;
	
	private final AssignRepository assignRepository;
	
	public BeneficiaryDTO getBeneficiary(BeneficiaryDTO beneficiaryDTO) {
		
		return dtoConverter.beneficiaryToDTO(
				beneficiaryRepository
				.findByEmail(beneficiaryDTO.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("Beneficiary Not Found: " + beneficiaryDTO.getEmail())));
	}
	
	public List<BeneficiaryDTO> getManagersBeneficiaries(ManagerDTO managerDTO) {
		
		return assignRepository.findByManager_Email(managerDTO.getEmail())
				.stream()
				.map(assign -> dtoConverter.beneficiaryToDTO(assign.getClaim().getBeneficiary()))
				.collect(Collectors.toList());
	}
}
