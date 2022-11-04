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
@Getter
@Setter
@ToString

public class ClaimResult {

	private ClaimDto claim;
	
	private InsuredDto insured;
	
	private BeneficiaryDto beneficiary;
	
	private IdentificationDto identification;
	
	private AnotherSubscribeDto anotherSubscribe;
	
	private AccountDto account;
	
	private AccidentDto accident;
}
