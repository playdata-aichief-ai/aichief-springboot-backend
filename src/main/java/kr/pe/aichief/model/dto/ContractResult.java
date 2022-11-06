package kr.pe.aichief.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString

public class ContractResult {
	
	private ContractDto contract;
	
	private InsuredDto insured;
	
	private BeneficiaryDto beneficiary;
	
	private InsuranceDto insurance;

}
