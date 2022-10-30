package kr.pe.aichief.util;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.pe.aichief.model.dto.AccidentDTO;
import kr.pe.aichief.model.dto.AccountDTO;
import kr.pe.aichief.model.dto.AnotherSubscribeDTO;
import kr.pe.aichief.model.dto.AssignDTO;
import kr.pe.aichief.model.dto.BeneficiaryDTO;
import kr.pe.aichief.model.dto.ClaimDTO;
import kr.pe.aichief.model.dto.IdentificationDTO;
import kr.pe.aichief.model.dto.InsuredDTO;
import kr.pe.aichief.model.dto.ManagerDTO;
import kr.pe.aichief.model.entity.Accident;
import kr.pe.aichief.model.entity.Account;
import kr.pe.aichief.model.entity.AnotherSubscribe;
import kr.pe.aichief.model.entity.Assign;
import kr.pe.aichief.model.entity.Beneficiary;
import kr.pe.aichief.model.entity.Claim;
import kr.pe.aichief.model.entity.Identification;
import kr.pe.aichief.model.entity.Insured;
import kr.pe.aichief.model.entity.Manager;

@Component
public class DTOConverter {
	
	/**
	 * Convert Manager.class to ManagerDTO.class
	 * @param manager
	 * @return ManagerDTO.class
	 */
	public ManagerDTO managerToDTO(Manager manager) {
		return ManagerDTO.builder()
				.managerId(manager.getManagerId())
				.name(manager.getName())
				.email(manager.getEmail())
				.phoneNumber(manager.getPhoneNumber())
				.joinDate(manager.getJoinDate())
				.build();
	}
	
	/**
	 * Convert Insured.class to InsuredDTO.class
	 * @param insured
	 * @return InsuredDTO.class
	 */
	public InsuredDTO insuredToDTO(Insured insured) {
		return InsuredDTO.builder()
				.insuredId(insured.getInsuredId())
				.name(insured.getName())
				.email(insured.getEmail())
				.phoneNumber(insured.getPhoneNumber())
				.joinDate(insured.getJoinDate())
				.socialSecurityNumber(insured.getSocialSecurityNumber())
				.job(insured.getJob())
				.build();
	}
	
	/**
	 * Convert Identification.class to IdentificationDTO.class
	 * @param identification
	 * @return IdentificationDTO.class
	 */
	public IdentificationDTO identificationToDTO(Identification identification) {
		return IdentificationDTO.builder()
				.identificationId(identification.getIdentificationId())
				.number(identification.getNumber())
				.serialNumber(identification.getSerialNumber())
				.issueDate(identification.getIssueDate())
				.issueBy(identification.getIssueBy())
				.build();
	}
	
	/**
	 * Convert AnotherSubscribe.class to AnotherSubscribeDTO.class
	 * @param anotherSubscribe
	 * @return AnotherSubscribeDTO.class
	 */
	public AnotherSubscribeDTO anotherSubscribeToDTO(AnotherSubscribe anotherSubscribe) {
		return AnotherSubscribeDTO.builder()
				.anotherSubscribeId(anotherSubscribe.getAnotherSubscribeId())
				.companyName(anotherSubscribe.getCompanyName())
				.number(anotherSubscribe.getNumber())
				.build();
	}
	
	/**
	 * Convert Account.class to AccountDTO.class
	 * @param account
	 * @return AccountDTO.class
	 */
	public AccountDTO accountToDTO(Account account) {
		return AccountDTO.builder()
				.accountId(account.getAccountId())
				.bankName(account.getBankName())
				.number(account.getNumber())
				.holder(account.getHolder())
				.build();
	}
	
	/**
	 * Convert Accident.class to AccidentDTO.class
	 * @param accident
	 * @return AccidentDTO.class
	 */
	public AccidentDTO accidentToDTO(Accident accident) {
		return AccidentDTO.builder()
				.accidentId(accident.getAccidentId())
				.dateTime(accident.getDateTime())
				.location(accident.getLocation())
				.details(accident.getDetails())
				.diseaseName(accident.getDiseaseName())
				.build();
	}
	
	/**
	 * Convert Beneficiary.class to BeneficiaryDTO.class
	 * @param beneficiary
	 * @return BeneficiaryDTO.class
	 */
	public BeneficiaryDTO beneficiaryToDTO(Beneficiary beneficiary) {
		return BeneficiaryDTO.builder()
				.beneficiaryId(beneficiary.getBeneficiaryId())
				.name(beneficiary.getName())
				.email(beneficiary.getEmail())
				.phoneNumber(beneficiary.getPhoneNumber())
				.joinDate(beneficiary.getJoinDate())
				.socialSecurityNumber(beneficiary.getSocialSecurityNumber())
				.job(beneficiary.getJob())
				.landline(beneficiary.getLandline())
				.address(beneficiary.getAddress())
				.relationshipWithInsured(beneficiary.getRelationshipWithInsured())
				.identificationDTO(identificationToDTO(beneficiary.getIdentification()))
				.anotherSubscribeDTOs(beneficiary.getAnotherSubscribes().stream().map(anotherSubscribe -> anotherSubscribeToDTO(anotherSubscribe)).collect(Collectors.toList()))
				.accountDTO(accountToDTO(beneficiary.getAccount()))
				.build();
	}
	
	/**
	 * Convert Claim.class to ClaimDTO.class
	 * @param claim
	 * @return ClaimDTO.class
	 */
	public ClaimDTO claimToDTO(Claim claim) {
		return ClaimDTO.builder()
				.claimId(claim.getClaimId())
				.insuredDTO(insuredToDTO(claim.getInsured()))
				.beneficiaryDTO(beneficiaryToDTO(claim.getBeneficiary()))
				.accidentDTO(accidentToDTO(claim.getAccident()))
				.reasonForPartialClaim(claim.getReasonForPartialClaim())
				.date(claim.getDate())
				.build();
	}
	
	/**
	 * Convert Assign.class to AssignDTO.class
	 * @param assign
	 * @return AssignDTO.class
	 */
	public AssignDTO assignToDTO(Assign assign) {
		return AssignDTO.builder()
				.assignId(assign.getAssignId())
				.managerDTO(managerToDTO(assign.getManager()))
				.claimDTO(claimToDTO(assign.getClaim()))
				.build();
	}
}
