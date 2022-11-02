package kr.pe.aichief.controller;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.aichief.model.dto.ClaimRequest;
import kr.pe.aichief.model.dto.MyResponse;
import kr.pe.aichief.model.service.ClaimService;
import kr.pe.aichief.model.service.MyPageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/claim")
@RequiredArgsConstructor
public class ClaimController {
	
	private final MyPageService myPageService;
	
	private final ClaimService claimService;
	
	@PostMapping
	public ResponseEntity<MyResponse> postClaim(@RequestBody ClaimRequest claimRequset) {
		
		MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
		
		result.getContents().add(claimService.claim(claimRequset));
		
		result.setCode(HttpStatus.OK.value());
		result.setHttpStatus(HttpStatus.OK);
		result.setCount(result.getContents().size());
		result.setMessage("Request POST Claim Success");
		
		return ResponseEntity.status(result.getHttpStatus()).body(result);
	}
	
	@GetMapping
	public ResponseEntity<MyResponse> getAllClaims(@RequestParam("email") String email) {
		
		MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
		
		String role = myPageService.getMemberRole(email);
		
		if(role.equals("beneficiary")) {
			result.setContents(myPageService.getAllBeneficiaryClaims(email).stream().map(claim -> (Object) claim).collect(Collectors.toList()));
		} else {
			result.setContents(myPageService.getAllManagerClaims(email).stream().map(claim -> (Object) claim).collect(Collectors.toList()));
		}
		
		result.setCode(HttpStatus.OK.value());
		result.setHttpStatus(HttpStatus.OK);
		result.setCount(result.getContents().size());
		result.setMessage("Request GET All Claims Success");
		
		return ResponseEntity.status(result.getHttpStatus()).body(result);
	}
}
