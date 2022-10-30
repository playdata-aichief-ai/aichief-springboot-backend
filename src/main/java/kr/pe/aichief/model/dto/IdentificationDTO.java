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

public class IdentificationDTO {

	private int identificationId;
	
	private String number;
	
	private String serialNumber;
	
	private LocalDate issueDate;
	
	private String issueBy;
}
