package kr.pe.aichief.controller;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import kr.pe.aichief.exceptions.InvalidInputException;
import kr.pe.aichief.model.dto.ClaimPostRequest;
import kr.pe.aichief.model.dto.ClaimPutRequest;
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
	public ResponseEntity<MyResponse> receiveClaim(@RequestBody ClaimPostRequest claimRequset) throws JsonMappingException, JsonProcessingException {
		
		MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
		
		result.getContents().add(claimService.serveClaim(claimRequset));
		
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
	
	@PutMapping
	public ResponseEntity<MyResponse> updateClaim(@RequestBody ClaimPutRequest claimRequest) 
			throws JsonMappingException, JsonProcessingException, InvalidInputException {
		
		MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
		
		claimService.updateClaim(claimRequest);
		
		result.setCode(HttpStatus.OK.value());
		result.setHttpStatus(HttpStatus.OK);
		result.setMessage("Request PUT Claim Success");
		return ResponseEntity.status(result.getHttpStatus()).body(result);
	}
	
	@DeleteMapping("/{claimId}")
	public ResponseEntity<MyResponse> deleteClaim(@PathVariable("claimId") int claimId) {
		
		MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
		
		claimService.deleteClaim(claimId);
		
		result.setCode(HttpStatus.OK.value());
		result.setHttpStatus(HttpStatus.OK);
		result.setMessage("Request DELETE Claim Success");
		return ResponseEntity.status(result.getHttpStatus()).body(result);
	}
}
