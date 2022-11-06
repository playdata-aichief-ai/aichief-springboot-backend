package kr.pe.aichief.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import kr.pe.aichief.model.dto.AccidentDto;
import kr.pe.aichief.model.dto.AccountDto;
import kr.pe.aichief.model.dto.AnotherSubscribeDto;
import kr.pe.aichief.model.dto.BeneficiaryDto;
import kr.pe.aichief.model.dto.ClaimDto;
import kr.pe.aichief.model.dto.ClaimResult;
import kr.pe.aichief.model.dto.ContractDto;
import kr.pe.aichief.model.dto.ContractResult;
import kr.pe.aichief.model.dto.IdentificationDto;
import kr.pe.aichief.model.dto.InsuranceDto;
import kr.pe.aichief.model.dto.InsuredDto;
import kr.pe.aichief.model.dto.ManagerDto;
import kr.pe.aichief.model.entity.Assign;
import kr.pe.aichief.model.entity.Beneficiary;
import kr.pe.aichief.model.entity.Claim;
import kr.pe.aichief.model.entity.Contract;
import kr.pe.aichief.model.entity.Manager;
import kr.pe.aichief.model.entity.Member;
import kr.pe.aichief.model.repository.AccidentRepository;
import kr.pe.aichief.model.repository.AccountRepository;
import kr.pe.aichief.model.repository.AnotherSubscribeRepository;
import kr.pe.aichief.model.repository.AssignRepository;
import kr.pe.aichief.model.repository.BeneficiaryRepository;
import kr.pe.aichief.model.repository.ClaimRepository;
import kr.pe.aichief.model.repository.ContractRepository;
import kr.pe.aichief.model.repository.IdentificationRepository;
import kr.pe.aichief.model.repository.InsuranceRepository;
import kr.pe.aichief.model.repository.InsuredRepository;
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
	
	private final AccidentRepository accidentRepository;
	
	private final InsuredRepository insuredRepository;
	
	private final IdentificationRepository identificationRepository;
	
	private final AnotherSubscribeRepository anotherSubscribeRepository;
	
	private final AccountRepository accountRepository;
	
	private final InsuranceRepository insuranceRepository;
	
	/**
	 * 회원의 역할 조회
	 * @param email
	 * @return role
	 */
	public String getMemberRole(String email) {
		
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Member Not Found: " + email));
		
		return member.getRole();
	}
	
	/**
	 * 이메일로 수익자 개인 정보 조회
	 * @param email
	 * @return beneficiary
	 */
	public BeneficiaryDto getBeneficiary(String email) {
		
		Beneficiary beneficiary = beneficiaryRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Beneficiary Not Found: " + email));
		
		BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(beneficiary);
		
		beneficiaryDto.setRole(memberRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Member Not Found")).getRole());
		
		return beneficiaryDto;
	}
	
	/**
	 * 이름으로 수익자들의 개인 정보 조회
	 * @param name
	 * @return beneficiaries
	 */
	public List<BeneficiaryDto> getBeneficiaryWithName(String name) {
		
		List<Beneficiary> beneficiaries = beneficiaryRepository.findByName(name);
		
		if(beneficiaries.isEmpty()) {
			throw new EntityNotFoundException("Beneficiary Not Found: " + name);
		}
		
		return beneficiaries.stream().map(beneficiary -> dtoConverter.beneficiaryToDto(beneficiary)).collect(Collectors.toList());
	}
	
	/**
	 * 이메일로 담당자 개인 정보 조회
	 * @param email
	 * @return manager
	 */
	public ManagerDto getManager(String email) {
		
		Manager manager = managerRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Manager Not Found: " + email));
		
		ManagerDto managerDto = dtoConverter.managerToDto(manager);
		
		managerDto.setRole(memberRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Member Not Found")).getRole());
		
		return managerDto;
	}
	
	/**
	 * 이메일로 수익자가 청구한 모든 청구 내역 조회
	 * @param email
	 * @return claims
	 */
	public List<ClaimResult> getAllBeneficiaryClaims(String email) {
		
		List<Claim> claims = claimRepository.findByContract_Beneficiary_Email(email);
		
		if(claims.isEmpty()) {
			throw new EntityNotFoundException("Claims Not Found");
		}
		
		List<ClaimResult> claimResults = new ArrayList<ClaimResult>();
		
		List<ClaimDto> claimDtos = claims.stream().map(claim -> dtoConverter.claimToDto(claim)).collect(Collectors.toList());
		for(ClaimDto dto : claimDtos) {
			
			// 계약
			ContractDto contractDto = dtoConverter.contractToDto(
					contractRepository.findByClaim_ClaimId(Integer.parseInt(dto.getClaimId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Contract not found")));

			// 사고
			AccidentDto accidentDto = dtoConverter.accidentToDto(
					accidentRepository.findByClaim_ClaimId(Integer.parseInt(dto.getClaimId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Accident not found")));

			// 피보험자
			InsuredDto insuredDto = dtoConverter.insuredToDto(insuredRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Insured not found")));

			// 수익자
			BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(beneficiaryRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Beneficiary not found")));

			// 신분증
			IdentificationDto identificationDto = dtoConverter.identificationToDto(identificationRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Identification not found")));

			// 타사 가입
			AnotherSubscribeDto anotherSubscribeDto = dtoConverter.anotherSubscribeToDto(anotherSubscribeRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
					.orElseThrow(() -> new EntityNotFoundException(
							"Get all beneficiary claims: AnotherSubscribe not found")));

			// 계좌
			AccountDto accountDto = dtoConverter.accountToDto(accountRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Account not found")));

			// 보험
			InsuranceDto insuranceDto = dtoConverter.insuranceToDto(insuranceRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Insurance not found")));

			claimResults.add(ClaimResult.builder()
					.claim(dto)
					.insured(insuredDto)
					.beneficiary(beneficiaryDto)
					.identification(identificationDto)
					.anotherSubscribe(anotherSubscribeDto)
					.account(accountDto)
					.accident(accidentDto)
					.contract(contractDto)
					.insurance(insuranceDto)
					.build());
		}
		
		return claimResults;
	}
	
	/**
	 * 이메일로 담당자가 배정 받은 모든 청구 내역 조회
	 * @param email
	 * @return claims
	 */
	public List<ClaimResult> getAllManagerClaims(String email) {
		
		List<Claim> claims = claimRepository.findByAssign_Manager_Email(email);
		
		if(claims.isEmpty()) {
			throw new EntityNotFoundException("Claims Not Found");
		}
		
		List<ClaimResult> claimResults = new ArrayList<ClaimResult>();
		
		List<ClaimDto> claimDtos = claims.stream().map(claim -> dtoConverter.claimToDto(claim)).collect(Collectors.toList());
		
		for(ClaimDto dto : claimDtos) {
			
			// 계약
			ContractDto contractDto = dtoConverter.contractToDto(
					contractRepository.findByClaim_ClaimId(Integer.parseInt(dto.getClaimId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Contract not found")));

			// 사고
			AccidentDto accidentDto = dtoConverter.accidentToDto(
					accidentRepository.findByClaim_ClaimId(Integer.parseInt(dto.getClaimId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Accident not found")));

			// 피보험자
			InsuredDto insuredDto = dtoConverter.insuredToDto(insuredRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Insured not found")));

			// 수익자
			BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(beneficiaryRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Beneficiary not found")));

			// 신분증
			IdentificationDto identificationDto = dtoConverter.identificationToDto(identificationRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Identification not found")));

			// 타사 가입
			AnotherSubscribeDto anotherSubscribeDto = dtoConverter.anotherSubscribeToDto(anotherSubscribeRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
					.orElseThrow(() -> new EntityNotFoundException(
							"Get all beneficiary claims: AnotherSubscribe not found")));

			// 계좌
			AccountDto accountDto = dtoConverter.accountToDto(accountRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Account not found")));

			// 보험
			InsuranceDto insuranceDto = dtoConverter.insuranceToDto(insuranceRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Insurance not found")));

			claimResults.add(ClaimResult.builder()
					.claim(dto)
					.insured(insuredDto)
					.beneficiary(beneficiaryDto)
					.identification(identificationDto)
					.anotherSubscribe(anotherSubscribeDto)
					.account(accountDto)
					.accident(accidentDto)
					.contract(contractDto)
					.insurance(insuranceDto)
					.build());
		}
		
		return claimResults;
	}
	
	/**
	 * 이메일로 수익자의 모든 계약 내역 조회
	 * @param email
	 * @return contracts
	 */
	public List<ContractResult> getAllBeneficiaryContracts(String email) {
		
		List<Contract> contracts = contractRepository.findByBeneficiary_Email(email);
		
		if(contracts.isEmpty() ) {
			throw new EntityNotFoundException("Contracts Not Found");
		}
		
		List<ContractResult> contractResults = new ArrayList<ContractResult>();
		
		List<ContractDto> contractDtos = contracts.stream().map(contract -> dtoConverter.contractToDto(contract)).collect(Collectors.toList());
		
		for(ContractDto dto : contractDtos) {
			
			// 피보험자
			InsuredDto insuredDto = dtoConverter.insuredToDto(insuredRepository.findByContracts_ContractId(Integer.parseInt(dto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary's contracts: Insured not found")));
			
			// 수익자
			BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(beneficiaryRepository.findByContracts_ContractId(Integer.parseInt(dto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary's contracts: Beneficiary not found")));
			
			// 보험
			InsuranceDto insuranceDto = dtoConverter.insuranceToDto(insuranceRepository.findByContracts_ContractId(Integer.parseInt(dto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary's contracts: Insurance not found")));
			
			contractResults.add(ContractResult.builder()
					.contract(dto)
					.insured(insuredDto)
					.beneficiary(beneficiaryDto)
					.insurance(insuranceDto)
					.build());
		}
		
		return contractResults;
	}
	
	/**
	 * 이메일로 담당자가 배정 받은 모든 수익자들의 개인 정보 조회
	 * @param email
	 * @return
	 */
	public List<BeneficiaryDto> getAllManagerBeneficiaries(String email) {
		
		List<Assign> assigns = assignRepository.findByManager_Email(email);
		
		if(assigns.isEmpty()) {
			throw new EntityNotFoundException("Assigns Not Found");
		}
		
		return assigns.stream().map(assign -> dtoConverter.beneficiaryToDto(assign.getClaim().getContract().getBeneficiary())).collect(Collectors.toList());
	}
}
