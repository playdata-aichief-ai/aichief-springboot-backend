package kr.pe.aichief.model.dto;

import kr.pe.aichief.model.dao.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString

public class ClientDTO {
	
	private int actorId;
	
	private String name;
	
	private String email;
	
	private String birthDay;
	
	private String phoneNumber;
	
	private String companyName;
	
	private String joinDate;
	
	private String socialSecurityNumber;
	
	private String address;
	
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
				.socialSecurityNumber(client.getSocialSecurityNumber())
				.address(client.getAddress())
				.nationality(client.getNationality())
				.englishName(client.getEnglishName())
				.build();
	}
}
