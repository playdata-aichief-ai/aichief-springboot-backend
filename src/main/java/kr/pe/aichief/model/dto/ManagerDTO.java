package kr.pe.aichief.model.dto;

import kr.pe.aichief.model.dao.Manager;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString

public class ManagerDTO {
	
	private int actorId;
	
	private String name;
	
	private String email;
	
	private String birthDay;
	
	private String phoneNumber;
	
	private String companyName;
	
	private String joinDate;
	
	private String state;
	
	public static ManagerDTO toManagerDTO(Manager manager) {
		return ManagerDTO.builder()
				.actorId(manager.getActorId())
				.name(manager.getName())
				.email(manager.getEmail())
				.birthDay(manager.getBirthDay())
				.phoneNumber(manager.getPhoneNumber())
				.companyName(manager.getCompanyName())
				.joinDate(manager.getJoinDate())
				.state(manager.getState())
				.build();
	}
}
