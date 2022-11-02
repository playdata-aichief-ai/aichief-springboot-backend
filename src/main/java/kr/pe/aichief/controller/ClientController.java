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
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

	private final MyPageService myPageService;
	
	@GetMapping
	public ResponseEntity<MyResponse> getAllClients(@RequestParam("email") String email) {
		
		MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
		
		result.setContents(myPageService.getAllManagerBeneficiaries(email).stream().map(client -> (Object) client).collect(Collectors.toList()));
		result.setCode(HttpStatus.OK.value());
		result.setHttpStatus(HttpStatus.OK);
		result.setCount(result.getContents().size());
		result.setMessage("Request GET All Beneficiaries Success");
		
		return ResponseEntity.status(result.getHttpStatus()).body(result);
	}
}
