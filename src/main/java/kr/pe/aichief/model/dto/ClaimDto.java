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

public class ClaimDto {

	private String claim;
	
	private String contract;
	
	private String insured;
	
	private String beneficiary;
	
	private String insurance;
	
	private String accident;
}
