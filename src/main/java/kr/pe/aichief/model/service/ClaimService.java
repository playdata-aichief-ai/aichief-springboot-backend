package kr.pe.aichief.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.pe.aichief.model.dto.BeneficiaryDTO;
import kr.pe.aichief.model.dto.ClaimDTO;
import kr.pe.aichief.model.dto.ManagerDTO;
import kr.pe.aichief.model.repository.AssignRepository;
import kr.pe.aichief.model.repository.ClaimRepository;
import kr.pe.aichief.util.DTOConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaimService {
	
	private final DTOConverter dtoConverter;
	
	private final ClaimRepository claimRepository;
	
	private final AssignRepository assignRepository;
	
	public List<ClaimDTO> getBeneficiaryClaims(BeneficiaryDTO beneficiaryDTO) {
		
		return claimRepository.findByBeneficiary_Email(beneficiaryDTO.getEmail())
				.stream()
				.map(claim -> dtoConverter.claimToDTO(claim))
				.collect(Collectors.toList());
	}
	
	public List<ClaimDTO> getManagersBeneficiaryClaims(ManagerDTO managerDTO, BeneficiaryDTO beneficiaryDTO) {
		
		return assignRepository.findByManager_EmailAndClaim_Beneficiary_Email(managerDTO.getEmail(), beneficiaryDTO.getEmail())
				.stream()
				.map(assign -> dtoConverter.claimToDTO(assign.getClaim()))
				.collect(Collectors.toList());
	}
}
