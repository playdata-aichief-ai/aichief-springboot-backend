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

public class ClaimPostRequest {
	
	private String user;
	
	private int contract_id;
	
	private String company;
	
	private String image_path;
}
