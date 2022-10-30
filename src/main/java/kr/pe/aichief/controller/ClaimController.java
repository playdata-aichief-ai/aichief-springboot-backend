package kr.pe.aichief.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.aichief.model.dto.BeneficiaryDTO;
import kr.pe.aichief.model.dto.ClaimDTO;
import kr.pe.aichief.model.dto.ManagerDTO;
import kr.pe.aichief.model.service.ClaimService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/claim")
@RequiredArgsConstructor
public class ClaimController {
	
	private final ClaimService claimService;
	
	@GetMapping("/beneficiary")
	public ResponseEntity<List<ClaimDTO>> getBeneficiaryClaims(@RequestParam("email") String email) {
		
		BeneficiaryDTO beneficiaryDTO = BeneficiaryDTO.builder().email(email).build();
		List<ClaimDTO> result = claimService.getBeneficiaryClaims(beneficiaryDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("/manager")
	public ResponseEntity<List<ClaimDTO>> getManagersBeneficiaryClaims(@RequestParam("manager_email") String managerEmail,
			@RequestParam("beneficiary_email") String beneficiaryEmail) {
		
		ManagerDTO managerDTO = ManagerDTO.builder().email(managerEmail).build();
		BeneficiaryDTO beneficiaryDTO = BeneficiaryDTO.builder().email(beneficiaryEmail).build();
		
		List<ClaimDTO> result = claimService.getManagersBeneficiaryClaims(managerDTO, beneficiaryDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
