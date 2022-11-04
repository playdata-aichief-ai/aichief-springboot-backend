package kr.pe.aichief.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.pe.aichief.model.entity.Claim;
import kr.pe.aichief.model.repository.ClaimRepository;
import kr.pe.aichief.model.repository.ManagerRepository;
import kr.pe.aichief.util.DtoConverter;

@Disabled
@SpringBootTest
public class MyPageServiceTest {
	
	@Autowired
	private DtoConverter dtoConverter;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private ClaimRepository claimRepository;
	
	private String managerEmail = "kim@test.com";
	
	private String beneficiaryEmail = "park@test.com";
	
	// 내 정보 수정 - 수익자
	
	// 내 계약 조회 - 수익자
	
	// 내 청구 조회 - 수익자
	@Disabled
	@Test
	void getAllBeneficiaryClaimsTest() {
		
		List<Claim> claims = claimRepository.findByContract_Beneficiary_Email(beneficiaryEmail);
		System.out.println(claims.stream().map(claim -> dtoConverter.claimToDto(claim)).collect(Collectors.toList()).toString());
	}
	
	// 내 정보 조회 - 담당자
	@Disabled
	@Test
	void getManagerTest() {
		System.out.println(dtoConverter.managerToDto(managerRepository.findByEmail(managerEmail).get()));
	}
	
	// 내 정보 수정 - 담당자
	
	// 내 고객 조회 - 담당자
	
	// 내 청구 조회 - 담당자
}
