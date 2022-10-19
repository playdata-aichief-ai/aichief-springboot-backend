package kr.pe.aichief.model.dao;

import javax.persistence.Entity;

import kr.pe.aichief.model.dto.ManagerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@ToString

@Entity
public class Manager extends Actor {
	
	private String state;
	
	public static Manager toManager(ManagerDTO managerDto) {
		return Manager.builder()
				.actorId(managerDto.getActorId())
				.name(managerDto.getName())
				.email(managerDto.getEmail())
				.birthDay(managerDto.getBirthDay())
				.phoneNumber(managerDto.getPhoneNumber())
				.companyName(managerDto.getCompanyName())
				.joinDate(managerDto.getJoinDate())
				.state(managerDto.getState())
				.build();
	}
}
