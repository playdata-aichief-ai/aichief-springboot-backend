package kr.pe.aichief.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString

public class IdentificationDto {

	private String identificationId;
	
	private String number;
	
	private String serialNumber;
	
	private String issueDate;
	
	private String issueBy;
}
