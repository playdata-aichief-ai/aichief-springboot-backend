package kr.pe.aichief.model.dto;

import java.time.LocalDateTime;

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

public class AccidentDTO {

	private int accidentId;
	
	private LocalDateTime dateTime;
	
	private String location;
	
	private String details;
	
	private String diseaseName;
}
