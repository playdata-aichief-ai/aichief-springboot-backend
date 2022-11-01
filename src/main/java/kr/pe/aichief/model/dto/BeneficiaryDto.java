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

public class BeneficiaryDto {
	
	private String beneficiary;
	
	private String identification;
	
	private String anotherSubscribes;
	
	private String account;
}
