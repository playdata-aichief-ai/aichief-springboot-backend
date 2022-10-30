package kr.pe.aichief.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.aichief.controller.dto.ResponseBeneficiary;
import kr.pe.aichief.model.dto.BeneficiaryDTO;
import kr.pe.aichief.model.dto.ManagerDTO;
import kr.pe.aichief.model.service.BeneficiaryService;
import kr.pe.aichief.util.ResponseConverter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/beneficiary")
@RequiredArgsConstructor
public class BeneficiaryController {
	
	private final ResponseConverter responseConverter;
	
	private final BeneficiaryService beneficiaryService;
	
	@GetMapping("/bymyself")
	public ResponseEntity<ResponseBeneficiary> getBeneficiary(@RequestParam("email") String email) {
		
		BeneficiaryDTO beneficiaryDTO = BeneficiaryDTO.builder().email(email).build();
		ResponseBeneficiary result = responseConverter
				.beneficiaryToResponse(beneficiaryService.getBeneficiary(beneficiaryDTO));
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("/bymanager")
	public ResponseEntity<List<ResponseBeneficiary>> getManagersBeneficiaries(@RequestParam("email") String email) {
		
		ManagerDTO managerDTO = ManagerDTO.builder().email(email).build();
		List<ResponseBeneficiary> result = responseConverter
				.beneficiariesToResponses(beneficiaryService.getManagersBeneficiaries(managerDTO));
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
}
