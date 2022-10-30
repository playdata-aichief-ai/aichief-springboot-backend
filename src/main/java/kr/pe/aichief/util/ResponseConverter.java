package kr.pe.aichief.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.pe.aichief.controller.dto.ResponseBeneficiary;
import kr.pe.aichief.model.dto.BeneficiaryDTO;

/**
 * Converts DTO instance to Response DTO instance
 * @author 박재민
 *
 */
@Component
public class ResponseConverter {
	
	/**
	 * Convert BeneficiaryDTO.class to ResponseBeneficiary.class
	 * @param beneficiaryDTO
	 * @return ResponseBeneficiary.class
	 */
	public ResponseBeneficiary beneficiaryToResponse(BeneficiaryDTO dto) {
		
		return ResponseBeneficiary.builder()
				.beneficiaryId(dto.getBeneficiaryId())
				.name(dto.getName())
				.email(dto.getEmail())
				.phoneNumber(dto.getPhoneNumber())
				.joinDate(dto.getJoinDate())
				.job(dto.getJob())
				.landline(dto.getLandline())
				.address(dto.getAddress())
				.relationshipWithInsured(dto.getRelationshipWithInsured())
				.build();
	}
	
	/**
	 * Convert List<BeneficiaryDTO.class> to List<ResponseBeneficiary.class>
	 * @param dtos
	 * @return List<ResponseBeneficiary.class>
	 */
	public List<ResponseBeneficiary> beneficiariesToResponses(List<BeneficiaryDTO> dtos) {
		
		return dtos.stream().map(dto -> beneficiaryToResponse(dto)).collect(Collectors.toList());
	}
}
