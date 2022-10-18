package kr.pe.aichief.model.dto;

import kr.pe.aichief.model.dao.Client;
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

public class ClientDTO {
	
	private String phoneNumber;
	
	private String name;
	
	private String socialSecurityNumber;
	
	private String address;
	
	private String email;
	
	private String companyName;
	
	private String job;
	
	private String nationality;
	
	private String englishName;
	
	/**
	 * Convert ClientDTO to Client
	 * @param client
	 * @return ClientDTO
	 */
	public static ClientDTO toClientDTO(Client client) {
		return ClientDTO.builder()
				.phoneNumber(client.getPhoneNumber())
				.name(client.getName())
				.socialSecurityNumber(client.getSocialSecurityNumber())
				.address(client.getAddress())
				.email(client.getEmail())
				.companyName(client.getCompanyName())
				.job(client.getJob())
				.nationality(client.getNationality())
				.englishName(client.getEnglishName())
				.build();
	}
}
