package kr.pe.aichief.model.dto;

import kr.pe.aichief.model.dao.Manager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString

public class ManagerDTO extends ActorDTO {
	
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
				.role(manager.getRole())
				.state(manager.getState())
				.build();
	}
}
