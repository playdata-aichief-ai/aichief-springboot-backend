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

public class ManagerDTO {
	
	private int managerId;
	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	private LocalDate joinDate;
}
