package kr.pe.aichief.model.dto;

import kr.pe.aichief.model.dao.Client;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString

public class ClientDTO extends ActorDTO {
	
	private String socialSecurityNumber;
	
	private String address;
	
	private String job;
	
	private String nationality;
	
	private String englishName;
	
	public static ClientDTO toClientDTO(Client client) {
		return ClientDTO.builder()
				.actorId(client.getActorId())
				.name(client.getName())
				.email(client.getEmail())
				.birthDay(client.getBirthDay())
				.phoneNumber(client.getPhoneNumber())
				.companyName(client.getCompanyName())
				.joinDate(client.getJoinDate())
				.role(client.getRole())
				.socialSecurityNumber(client.getSocialSecurityNumber())
				.address(client.getAddress())
				.job(client.getJob())
				.nationality(client.getNationality())
				.englishName(client.getEnglishName())
				.build();
	}
}
