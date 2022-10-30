package kr.pe.aichief.controller.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString

public class ResponseBeneficiary {

	private int beneficiaryId;
	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	private LocalDate joinDate;
	
	private String job;
	
	private String landline;
	
	private String address;
	
	private String relationshipWithInsured;
}
