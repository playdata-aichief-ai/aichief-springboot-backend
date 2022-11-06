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

public class ManagerDto {
	
	private String managerId;
	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	private String joinDate;
	
	private String role;
}
