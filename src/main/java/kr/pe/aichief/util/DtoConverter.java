package kr.pe.aichief.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import kr.pe.aichief.model.dto.AccidentDto;
import kr.pe.aichief.model.dto.AccountDto;
import kr.pe.aichief.model.dto.AnotherSubscribeDto;
import kr.pe.aichief.model.dto.AssignDto;
import kr.pe.aichief.model.dto.BeneficiaryDto;
import kr.pe.aichief.model.dto.ClaimDto;
import kr.pe.aichief.model.dto.ContractDto;
import kr.pe.aichief.model.dto.IdentificationDto;
import kr.pe.aichief.model.dto.InsuranceDto;
import kr.pe.aichief.model.dto.InsuredDto;
import kr.pe.aichief.model.dto.ManagerDto;
import kr.pe.aichief.model.entity.Accident;
import kr.pe.aichief.model.entity.Account;
import kr.pe.aichief.model.entity.AnotherSubscribe;
import kr.pe.aichief.model.entity.Assign;
import kr.pe.aichief.model.entity.Beneficiary;
import kr.pe.aichief.model.entity.Claim;
import kr.pe.aichief.model.entity.Contract;
import kr.pe.aichief.model.entity.Identification;
import kr.pe.aichief.model.entity.Insurance;
import kr.pe.aichief.model.entity.Insured;
import kr.pe.aichief.model.entity.Manager;

@Component
public class DtoConverter {

	public BeneficiaryDto beneficiaryToDto(Beneficiary beneficiary) {

		return BeneficiaryDto.builder()
				.beneficiaryId(Integer.toString(beneficiary.getBeneficiaryId()))
				.name(beneficiary.getName())
				.email(beneficiary.getEmail())
				.phoneNumber(beneficiary.getPhoneNumber())
				.joinDate(beneficiary.getJoinDate().toString())
				.socialSecurityNumber(beneficiary.getSocialSecurityNumber())
				.job(Optional.ofNullable(beneficiary.getJob()).orElse("-"))
				.landline(Optional.ofNullable(beneficiary.getLandline()).orElse("-"))
				.address(beneficiary.getAddress())
				.relationshipWithInsured(Optional.ofNullable(beneficiary.getRelationshipWithInsured()).orElse("-"))
				.build();
	}

	public ClaimDto claimToDto(Claim claim) {

		return ClaimDto.builder()
				.claimId(Integer.toString(claim.getClaimId()))
				.reasonForPartialClaim(Optional.ofNullable(claim.getReasonForPartialClaim()).orElse("-"))
				.date(claim.getDate().toString())
				.build();

	}

	public ContractDto contractToDto(Contract contract) {
		
		ContractDto dto = ContractDto.builder()
				.contractId(Integer.toString(contract.getContractId()))
				.state(Optional.ofNullable(contract.getState()).orElse("-"))
				.build();
		
		if(!Optional.ofNullable(contract.getMonthlyPremium()).isEmpty()) {
			dto.setMonthlyPremium(Float.toString(contract.getMonthlyPremium()));
		}

		return dto;
	}

	public ManagerDto managerToDto(Manager manager) {

		return ManagerDto.builder()
				.managerId(Integer.toBinaryString(manager.getManagerId()))
				.name(manager.getName())
				.email(manager.getEmail())
				.phoneNumber(manager.getPhoneNumber())
				.joinDate(manager.getJoinDate().toString())
				.build();
	}
	
	public IdentificationDto identificationToDto(Identification iden) {
		
		IdentificationDto dto = IdentificationDto.builder()
				.identificationId(Integer.toString(iden.getIdentificationId()))
				.number(iden.getNumber())
				.serialNumber(Optional.ofNullable(iden.getSerialNumber()).orElse("-"))
				.issueBy(Optional.ofNullable(iden.getIssueBy()).orElse("-"))
				.build();
		
		if(!Optional.ofNullable(iden.getIssueDate()).isEmpty()) {
			dto.setIssueDate(iden.getIssueDate().toString());
		}
		
		return dto;
	}
	
	public AnotherSubscribeDto anotherSubscribeToDto(AnotherSubscribe as) {
		
		return AnotherSubscribeDto.builder()
				.anotherSubscribeId(Integer.toString(as.getAnotherSubscribeId()))
				.companyName(as.getCompanyName())
				.number(Integer.toString(Optional.ofNullable(as.getNumber()).orElse(0)))
				.build();
	}
	
	public AccountDto accountToDto(Account acc) {
		
		return AccountDto.builder()
				.accountId(Integer.toString(acc.getAccountId()))
				.bankName(acc.getBankName())
				.number(acc.getNumber())
				.holder(acc.getHolder())
				.build();
	}
	
	public InsuredDto insuredToDto(Insured ins) {
		
		return InsuredDto.builder()
				.insuredId(Integer.toString(ins.getInsuredId()))
				.name(ins.getName())
				.email(ins.getEmail())
				.phoneNumber(ins.getPhoneNumber())
				.joinDate(ins.getJoinDate().toString())
				.socialSecurityNumber(ins.getSocialSecurityNumber())
				.job(Optional.ofNullable(ins.getJob()).orElse("-"))
				.build();
	}
	
	public InsuranceDto insuranceToDto(Insurance ins) {
		
		return InsuranceDto.builder()
				.insuranceId(Integer.toString(ins.getInsuranceId()))
				.companyName(ins.getCompanyName())
				.details(Optional.ofNullable(ins.getDetails()).orElse("-"))
				.build();
	}
	
	public AccidentDto accidentToDto(Accident acc) {
		
		return AccidentDto.builder()
				.accidentId(Integer.toString(acc.getAccidentId()))
				.dateTime(acc.getDateTime().toString())
				.location(Optional.ofNullable(acc.getLocation()).orElse("-"))
				.details(acc.getDetails())
				.diseaseName(Optional.ofNullable(acc.getDiseaseName()).orElse("-"))
				.build();
	}
	
	public AssignDto assignToDto(Assign ass) {
		
		return AssignDto.builder()
				.assignId(Integer.toString(ass.getAssignId()))
				.build();
	}
}
