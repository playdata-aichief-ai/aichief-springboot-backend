package kr.pe.aichief.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.pe.aichief.model.dto.BeneficiaryDto;
import kr.pe.aichief.model.dto.ClaimDto;
import kr.pe.aichief.model.dto.ContractDto;
import kr.pe.aichief.model.dto.ManagerDto;
import kr.pe.aichief.model.entity.Beneficiary;
import kr.pe.aichief.model.entity.Claim;
import kr.pe.aichief.model.entity.Contract;
import kr.pe.aichief.model.entity.Manager;

@Component
public class DtoConverter {

	public BeneficiaryDto beneficiaryToDto(Beneficiary beneficiary) {

		Map<String, String> beneMap = new HashMap<String, String>();

		beneMap.put("beneficiaryId", Integer.toString(beneficiary.getBeneficiaryId()));
		beneMap.put("email", beneficiary.getEmail());
		beneMap.put("phoneNumber", beneficiary.getPhoneNumber());
		beneMap.put("joinDate", beneficiary.getJoinDate().toString());
		beneMap.put("job", beneficiary.getJob());
		beneMap.put("landline", beneficiary.getLandline());
		beneMap.put("address", beneficiary.getAddress());
		beneMap.put("relationshipWithInsured", beneficiary.getRelationshipWithInsured());

		return BeneficiaryDto.builder().beneficiary(beneMap).build();
	}

	public ClaimDto claimToDto(Claim claim) {

		Map<String, String> claMap = new HashMap<String, String>();

		claMap.put("claimId", Integer.toString(claim.getClaimId()));
		claMap.put("reasonForPartialClaim", claim.getReasonForPartialClaim());
		claMap.put("date", claim.getDate().toString());

		Map<String, String> insMap = new HashMap<String, String>();

		insMap.put("insuredId", Integer.toString(claim.getContract().getInsured().getInsuredId()));
		insMap.put("name", claim.getContract().getInsured().getName());
		insMap.put("email", claim.getContract().getInsured().getEmail());
		insMap.put("phoneNumber", claim.getContract().getInsured().getPhoneNumber());
		insMap.put("joinDate", claim.getContract().getInsured().getJoinDate().toString());
		insMap.put("job", claim.getContract().getInsured().getJob());

		Map<String, String> benMap = new HashMap<String, String>();

		benMap.put("beneficiaryId", Integer.toString(claim.getContract().getBeneficiary().getBeneficiaryId()));
		benMap.put("name", claim.getContract().getBeneficiary().getName());
		benMap.put("email", claim.getContract().getBeneficiary().getEmail());
		benMap.put("phoneNumber", claim.getContract().getBeneficiary().getPhoneNumber());
		benMap.put("joinDate", claim.getContract().getBeneficiary().getJoinDate().toString());
		benMap.put("job", claim.getContract().getBeneficiary().getJob());
		benMap.put("landline", claim.getContract().getBeneficiary().getLandline());
		benMap.put("address", claim.getContract().getBeneficiary().getAddress());
		benMap.put("relationshipWithInsured", claim.getContract().getBeneficiary().getRelationshipWithInsured());

		Map<String, String> insuMap = new HashMap<String, String>();

		insuMap.put("insuranceId", Integer.toString(claim.getContract().getInsurance().getInsuranceId()));
		insuMap.put("companyName", claim.getContract().getInsurance().getCompanyName());
		insuMap.put("details", claim.getContract().getInsurance().getDetails());

		Map<String, String> accMap = new HashMap<String, String>();

		accMap.put("accidentId", Integer.toString(claim.getAccident().getAccidentId()));
		accMap.put("accidentDateTime", claim.getAccident().getDateTime().toString());
		accMap.put("location", claim.getAccident().getLocation());
		accMap.put("details", claim.getAccident().getDetails());
		accMap.put("diseaseName", claim.getAccident().getDiseaseName());

		return ClaimDto.builder().claim(claMap).insured(insMap).beneficiary(benMap).insurance(insuMap).accident(accMap)
				.build();

	}

	public ContractDto contractToDto(Contract contract) {

		Map<String, String> conMap = new HashMap<String, String>();

		conMap.put("contractId", Integer.toString(contract.getContractId()));
		conMap.put("monthlyPremium", Float.toString(contract.getMonthlyPremium()));
		conMap.put("state", contract.getState());

		Map<String, String> insMap = new HashMap<String, String>();

		insMap.put("insuredId", Integer.toString(contract.getInsured().getInsuredId()));
		insMap.put("name", contract.getInsured().getName());
		insMap.put("email", contract.getInsured().getEmail());
		insMap.put("phoneNumber", contract.getInsured().getPhoneNumber());
		insMap.put("joinDate", contract.getInsured().getJoinDate().toString());
		insMap.put("job", contract.getInsured().getJob());

		Map<String, String> benMap = new HashMap<String, String>();

		benMap.put("beneficiaryId", Integer.toString(contract.getBeneficiary().getBeneficiaryId()));
		benMap.put("name", contract.getBeneficiary().getName());
		benMap.put("email", contract.getBeneficiary().getEmail());
		benMap.put("phoneNumber", contract.getBeneficiary().getPhoneNumber());
		benMap.put("joinDate", contract.getBeneficiary().getJoinDate().toString());
		benMap.put("job", contract.getBeneficiary().getJob());
		benMap.put("landline", contract.getBeneficiary().getLandline());
		benMap.put("address", contract.getBeneficiary().getAddress());
		benMap.put("relationshipWithInsured", contract.getBeneficiary().getRelationshipWithInsured());

		Map<String, String> insuMap = new HashMap<String, String>();

		insuMap.put("insuranceId", Integer.toString(contract.getInsurance().getInsuranceId()));
		insuMap.put("companyName", contract.getInsurance().getCompanyName());
		insuMap.put("details", contract.getInsurance().getDetails());
		
		return ContractDto.builder()
				.contract(conMap)
				.insured(insMap)
				.beneficiary(benMap)
				.insurance(insuMap)
				.build();
	}

	public ManagerDto managerToDto(Manager manager) {

		Map<String, String> manaMap = new HashMap<String, String>();

		manaMap.put("managerId", Integer.toString(manager.getManagerId()));
		manaMap.put("name", manager.getName());
		manaMap.put("email", manager.getEmail());
		manaMap.put("phoneNumber", manager.getPhoneNumber());
		manaMap.put("joinDate", manager.getJoinDate().toString());

		return ManagerDto.builder().manager(manaMap).build();
	}
}
