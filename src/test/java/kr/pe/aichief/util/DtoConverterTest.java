package kr.pe.aichief.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import kr.pe.aichief.model.entity.Account;
import kr.pe.aichief.model.entity.AnotherSubscribe;
import kr.pe.aichief.model.entity.Beneficiary;
import kr.pe.aichief.model.entity.Identification;
import kr.pe.aichief.model.repository.BeneficiaryRepository;

@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DtoConverterTest {
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;

	@Test
	public void beneficiaryConverterTest() {
		
		String email = "park001@test.com";
		
		// DtoConverter 생성
		DtoConverter dtoConverter = new DtoConverter();

		// Identification 생성
		Identification identification = Identification.builder().number("1234561234567").build();

		// List<AnotherSubscribe> 생성
		List<AnotherSubscribe> anotherSubscribes = new ArrayList<AnotherSubscribe>();
		anotherSubscribes.add(AnotherSubscribe.builder().companyName("kb").number(1).build());

		// Account 생성
		Account account = Account.builder().bankName("shinhan").number("123456789012").holder("박아무개").build();

		// Beneficiary 생성
		Beneficiary beneficiary = Beneficiary.builder().name("박아무개").email("park001@test.com")
				.phoneNumber("01022223333").joinDate(LocalDate.of(2022, 10, 20)).socialSecurityNumber("1234561234567")
				.address("서울특별시 어딘가").identification(identification)
				.anotherSubscribes(anotherSubscribes).account(account).build();
		
		beneficiaryRepository.save(beneficiary);
		beneficiaryRepository.flush();
		
		System.out.println(dtoConverter.beneficiaryToDto(beneficiaryRepository.findByEmail(email).get()));
	}
}
