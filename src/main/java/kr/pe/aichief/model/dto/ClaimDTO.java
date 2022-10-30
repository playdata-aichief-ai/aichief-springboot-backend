package kr.pe.aichief.model.dto;

import java.time.LocalDate;

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

public class ClaimDTO {

	private int claimId;
	
	private InsuredDTO insuredDTO;
	
	private BeneficiaryDTO beneficiaryDTO;
	
	private AccidentDTO accidentDTO;
	
	private String reasonForPartialClaim;
	
	private LocalDate date;
}
