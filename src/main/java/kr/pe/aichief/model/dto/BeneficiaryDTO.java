package kr.pe.aichief.model.dto;

import java.time.LocalDate;
import java.util.List;

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

public class BeneficiaryDTO {
	
	private int beneficiaryId;
	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	private LocalDate joinDate;
	
	private String socialSecurityNumber;
	
	private String job;
	
	private String landline;
	
	private String address;
	
	private String relationshipWithInsured;
	
	private IdentificationDTO identificationDTO;
	
	private List<AnotherSubscribeDTO> anotherSubscribeDTOs;
	
	private AccountDTO accountDTO;
}
