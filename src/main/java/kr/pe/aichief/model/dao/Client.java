package kr.pe.aichief.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import kr.pe.aichief.model.dto.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString

@Entity
public class Client {
	
	@Id
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column
	private String name;
	
	@Column(name = "social_security_number")
	private String socialSecurityNumber;
	
	@Column
	private String address;
	
	@Column
	private String email;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column
	private String job;
	
	@Column
	private String nationality;
	
	@Column(name = "english_name")
	private String englishName;
	
	/**
	 * Convert ClientDTO to Client
	 * @param dto
	 * @return Client
	 */
	public static Client toClient(ClientDTO dto) {
		return Client.builder()
				.phoneNumber(dto.getPhoneNumber())
				.name(dto.getName())
				.socialSecurityNumber(dto.getSocialSecurityNumber())
				.address(dto.getAddress())
				.email(dto.getEmail())
				.companyName(dto.getCompanyName())
				.job(dto.getJob())
				.nationality(dto.getNationality())
				.englishName(dto.getEnglishName())
				.build();
	}
}
