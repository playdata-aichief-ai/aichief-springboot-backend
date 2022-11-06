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

	private String beneficiaryId;
	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	private String joinDate;
	
	private String socialSecurityNumber;
	
	private String job;
	
	private String landline;
	
	private String address;
	
	private String relationshipWithInsured;
	
	private String role;
}
