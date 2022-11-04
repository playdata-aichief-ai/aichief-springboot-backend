package kr.pe.aichief.controller;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.aichief.model.dto.MyResponse;
import kr.pe.aichief.model.service.MyPageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {
	
	private final MyPageService myPageService;
	
	@GetMapping
	public ResponseEntity<MyResponse> getInfo(@RequestParam("email") String email) {
		
		MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
		String role = myPageService.getMemberRole(email);
		
		if(role.equals("beneficiary")) {
			result.getContents().add(myPageService.getBeneficiary(email));
		} else {
			result.getContents().add(myPageService.getManager(email));
		}
		
		result.setCode(HttpStatus.OK.value());
		result.setHttpStatus(HttpStatus.OK);
		result.setCount(result.getContents().size());
		result.setMessage("Request get info success");
		
		return ResponseEntity.status(result.getHttpStatus()).body(result);
	}
	
	@GetMapping("/with_name")
	public ResponseEntity<MyResponse> getInfoWithName(@RequestParam("name") String name) {
		
		MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
		
		result.setContents(myPageService.getBeneficiaryWithName(name).stream().map(beneficiary -> (Object) beneficiary).collect(Collectors.toList()));
		result.setCode(HttpStatus.OK.value());
		result.setHttpStatus(HttpStatus.OK);
		result.setCount(result.getContents().size());
		result.setMessage("Request get info success");
		
		return ResponseEntity.status(result.getHttpStatus()).body(result);
	}
}
