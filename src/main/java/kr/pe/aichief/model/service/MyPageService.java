package kr.pe.aichief.model.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import kr.pe.aichief.model.dto.BeneficiaryDto;
import kr.pe.aichief.model.dto.ClaimDto;
import kr.pe.aichief.model.dto.ContractDto;
import kr.pe.aichief.model.dto.ManagerDto;
import kr.pe.aichief.model.entity.Assign;
import kr.pe.aichief.model.entity.Beneficiary;
import kr.pe.aichief.model.entity.Claim;
import kr.pe.aichief.model.entity.Contract;
import kr.pe.aichief.model.entity.Manager;
import kr.pe.aichief.model.entity.Member;
import kr.pe.aichief.model.repository.AssignRepository;
import kr.pe.aichief.model.repository.BeneficiaryRepository;
import kr.pe.aichief.model.repository.ClaimRepository;
import kr.pe.aichief.model.repository.ContractRepository;
import kr.pe.aichief.model.repository.ManagerRepository;
import kr.pe.aichief.model.repository.MemberRepository;
import kr.pe.aichief.util.DtoConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageService {
	
	private final DtoConverter dtoConverter;
	
	private final MemberRepository memberRepository;
	
	private final BeneficiaryRepository beneficiaryRepository;
	
	private final ManagerRepository managerRepository;
	
	private final ClaimRepository claimRepository;
	
	private final ContractRepository contractRepository;
	
	private final AssignRepository assignRepository;
	
	public String getMemberRole(String email) {
		
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Member Not Found: " + email));
		
		return member.getRole();
	}
	
	public BeneficiaryDto getBeneficiary(String email) {
		
		Beneficiary beneficiary = beneficiaryRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Beneficiary Not Found: " + email));
		
		return dtoConverter.beneficiaryToDto(beneficiary);
	}
	
	public ManagerDto getManager(String email) {
		
		Manager manager = managerRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Manager Not Found: " + email));
		
		return dtoConverter.managerToDto(manager);
	}
	
	public List<ClaimDto> getAllBeneficiaryClaims(String email) {
		
		List<Claim> claims = claimRepository.findByContract_Beneficiary_Email(email);
		
		if(claims.isEmpty()) {
			throw new EntityNotFoundException("Claims Not Found");
		}
		
		return claims.stream().map(claim -> dtoConverter.claimToDto(claim)).collect(Collectors.toList());
	}
	
	public List<ClaimDto> getAllManagerClaims(String email) {
		
		List<Claim> claims = claimRepository.findByAssign_Manager_Email(email);
		
		if(claims.isEmpty()) {
			throw new EntityNotFoundException("Claims Not Found");
		}
		
		return claims.stream().map(claim -> dtoConverter.claimToDto(claim)).collect(Collectors.toList());
	}
	
	public List<ContractDto> getAllBeneficiaryContracts(String email) {
		
		List<Contract> contracts = contractRepository.findByBeneficiary_Email(email);
		
		if(contracts.isEmpty() ) {
			throw new EntityNotFoundException("Contracts Not Found");
		}
		
		return contracts.stream().map(contract -> dtoConverter.contractToDto(contract)).collect(Collectors.toList());
	}
	
	public List<BeneficiaryDto> getAllManagerBeneficiaries(String email) {
		
		List<Assign> assigns = assignRepository.findByManager_Email(email);
		
		if(assigns.isEmpty()) {
			throw new EntityNotFoundException("Assigns Not Found");
		}
		
		return assigns.stream().map(assign -> dtoConverter.beneficiaryToDto(assign.getClaim().getContract().getBeneficiary())).collect(Collectors.toList());
	}
}
