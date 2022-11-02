package kr.pe.aichief.model.dto;

import java.util.Map;

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

public class ContractDto {

	private Map<String, String> contract;
	
	private Map<String, String> insured;
	
	private Map<String, String> beneficiary;
	
	private Map<String, String> insurance;
}
