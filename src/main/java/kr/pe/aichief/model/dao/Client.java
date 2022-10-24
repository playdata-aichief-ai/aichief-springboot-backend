package kr.pe.aichief.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;

import kr.pe.aichief.model.dto.ClientDTO;
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
public class Client extends Actor {
	
	@Column(name = "social_security_number")
	private String socialSecurityNumber;
	
	private String job;
	
	private String address;
	
	private String nationality;
	
	@Column(name = "english_name")
	private String englishName;
	
	public static Client toClient(ClientDTO clientDto) {
		return Client.builder()
				.actorId(clientDto.getActorId())
				.name(clientDto.getName())
				.email(clientDto.getEmail())
				.birthDay(clientDto.getBirthDay())
				.phoneNumber(clientDto.getPhoneNumber())
				.companyName(clientDto.getCompanyName())
				.joinDate(clientDto.getJoinDate())
				.role(clientDto.getRole())
				.socialSecurityNumber(clientDto.getSocialSecurityNumber())
				.job(clientDto.getJob())
				.address(clientDto.getAddress())
				.nationality(clientDto.getNationality())
				.englishName(clientDto.getEnglishName())
				.build();
	}
}
