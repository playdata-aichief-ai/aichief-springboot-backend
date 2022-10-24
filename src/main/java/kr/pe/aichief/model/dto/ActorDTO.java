package kr.pe.aichief.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString

public class ActorDTO {

	private int actorId;
	
	private String name;
	
	private String email;
	
	private String birthDay;
	
	private String phoneNumber;
	
	private String companyName;
	
	private String joinDate;
	
	private String role;
}
