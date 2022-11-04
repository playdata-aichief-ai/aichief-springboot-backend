package kr.pe.aichief.util;

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
				.job(beneficiary.getJob())
				.landline(beneficiary.getLandline())
				.address(beneficiary.getAddress())
				.relationshipWithInsured(beneficiary.getRelationshipWithInsured())
				.build();
	}

	public ClaimDto claimToDto(Claim claim) {

		return ClaimDto.builder()
				.claimId(Integer.toString(claim.getClaimId()))
				.reasonForPartialClaim(claim.getReasonForPartialClaim())
				.date(claim.getDate().toString())
				.build();

	}

	public ContractDto contractToDto(Contract contract) {

		return ContractDto.builder()
				.contractId(Integer.toString(contract.getContractId()))
				.monthlyPremium(Float.toString(contract.getMonthlyPremium()))
				.state(contract.getState())
				.build();
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
		
		return IdentificationDto.builder()
				.identificationId(Integer.toString(iden.getIdentificationId()))
				.number(iden.getNumber())
				.serialNumber(iden.getSerialNumber())
				.issueDate(iden.getIssueDate().toString())
				.issueBy(iden.getIssueBy())
				.build();
	}
	
	public AnotherSubscribeDto anotherSubscribeToDto(AnotherSubscribe as) {
		
		return AnotherSubscribeDto.builder()
				.anotherSubscribeId(Integer.toString(as.getAnotherSubscribeId()))
				.companyName(as.getCompanyName())
				.number(Integer.toString(as.getNumber()))
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
				.job(ins.getJob())
				.build();
	}
	
	public InsuranceDto insuranceToDto(Insurance ins) {
		
		return InsuranceDto.builder()
				.insuranceId(Integer.toString(ins.getInsuranceId()))
				.companyName(ins.getCompanyName())
				.details(ins.getDetails())
				.build();
	}
	
	public AccidentDto accidentToDto(Accident acc) {
		
		return AccidentDto.builder()
				.accidentId(Integer.toString(acc.getAccidentId()))
				.dateTime(acc.getDateTime().toString())
				.location(acc.getLocation())
				.details(acc.getDetails())
				.diseaseName(acc.getDiseaseName())
				.build();
	}
	
	public AssignDto assignToDto(Assign ass) {
		
		return AssignDto.builder()
				.assignId(Integer.toString(ass.getAssignId()))
				.build();
	}
}
