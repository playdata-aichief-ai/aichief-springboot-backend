package kr.pe.aichief.util;

import java.util.stream.Collectors;

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
		return BeneficiaryDto.builder()
				.beneficiary(beneficiary.toString())
				.identification(beneficiary.getIdentification().toString())
				.anotherSubscribes(beneficiary.getAnotherSubscribes().stream().map(as -> as.toString()).collect(Collectors.toList()).toString())
				.account(beneficiary.getAccount().toString())
				.build();
	}
	
	public ClaimDto claimToDto(Claim claim) {
		return ClaimDto.builder()
				.claim(claim.toString())
				.contract(claim.getContract().toString())
				.insured(claim.getContract().getInsured().toString())
				.beneficiary(claim.getContract().getBeneficiary().toString())
				.insurance(claim.getContract().getInsurance().toString())
				.accident(claim.getAccident().toString())
				.build();
	}
	
	public ContractDto contractToDto(Contract contract) {
		return ContractDto.builder()
				.contract(contract.toString())
				.insured(contract.getInsured().toString())
				.beneficiary(contract.getBeneficiary().toString())
				.insurance(contract.getInsurance().toString())
				.build();
	}
	
	public ManagerDto managerToDto(Manager manager) {
		return ManagerDto.builder()
				.manager(manager.toString())
				.build();
	}
}
