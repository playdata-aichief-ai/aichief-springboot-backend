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

public class InsuredDto {

	private String insuredId;
	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	private String joinDate;
	
	private String socialSecurityNumber;
	
	private String job;
}
