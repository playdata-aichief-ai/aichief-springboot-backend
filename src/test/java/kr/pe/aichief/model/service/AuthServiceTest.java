package kr.pe.aichief.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import kr.pe.aichief.model.entity.Insured;
import kr.pe.aichief.model.entity.Manager;
import kr.pe.aichief.model.entity.Member;
import kr.pe.aichief.model.repository.BeneficiaryRepository;
import kr.pe.aichief.model.repository.ManagerRepository;
import kr.pe.aichief.model.repository.MemberRepository;

@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AuthServiceTest {

	@Autowired
	private BeneficiaryRepository beneficiaryRepository;

	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private MemberRepository memberRepository;

	@Disabled
	@Test
	public void beneficiaryCrudTest() {

		// Identification 생성
		Identification identification = Identification.builder().number("1234561234567").build();

		// List<AnotherSubscribe> 생성
		List<AnotherSubscribe> anotherSubscribes = new ArrayList<AnotherSubscribe>();
		anotherSubscribes.add(AnotherSubscribe.builder().companyName("kb").number(1).build());

		// Account 생성
		Account account = Account.builder().bankName("shinhan").number("123456789012").holder("박아무개").build();

		// Insured 생성
		List<Insured> insureds = new ArrayList<Insured>();
		insureds.add(Insured.builder()
				.name("김아무개")
				.email("kim001@test.com")
				.phoneNumber("01011112222")
				.joinDate(LocalDate.of(2022, 10, 20))
				.socialSecurityNumber("6543217654321").build());

		// Beneficiary 생성
		Beneficiary beneficiary = Beneficiary.builder()
				.name("박아무개")
				.email("park001@test.com")
				.phoneNumber("01022223333")
				.joinDate(LocalDate.of(2022, 10, 20))
				.socialSecurityNumber("1234561234567")
				.address("서울특별시 어딘가").insureds(insureds)
				.identification(identification)
				.anotherSubscribes(anotherSubscribes)
				.account(account).build();

		// Beneficiary 저장
		beneficiaryRepository.save(beneficiary);
		beneficiaryRepository.flush();

		// Beneficiary 조회
		Optional<Beneficiary> savedBeneficiary = beneficiaryRepository.findByEmail("park001@test.com");
		savedBeneficiary.ifPresent(bene -> System.out.println(bene));

		// Beneficiary 수정
		savedBeneficiary.ifPresent(bene -> bene.setJob("개발자"));
		beneficiaryRepository.flush();
		beneficiaryRepository.findByEmail("park001@test.com").ifPresent(bene -> System.out.println(bene));

		// Beneficiary 삭제
		savedBeneficiary.ifPresent(bene -> beneficiaryRepository.delete(bene));
		beneficiaryRepository.flush();
		System.out.println(beneficiaryRepository.findByEmail("park001@test.com").isEmpty());
	}

	@Disabled
	@Test
	public void beneficiarySignInTest() {
		
		String inputEmail = "Kang001@test.com";

		// Identification 생성
		Identification identification = Identification.builder().number("1234561234567").build();

		// List<AnotherSubscribe> 생성
		List<AnotherSubscribe> anotherSubscribes = new ArrayList<AnotherSubscribe>();
		anotherSubscribes.add(AnotherSubscribe.builder().companyName("kb").number(1).build());

		// Account 생성
		Account account = Account.builder().bankName("shinhan").number("123456789012").holder("박아무개").build();

		// Insured 생성
		List<Insured> insureds = new ArrayList<Insured>();
		insureds.add(Insured.builder().name("김아무개").email("kim001@test.com").phoneNumber("01011112222")
				.joinDate(LocalDate.of(2022, 10, 20)).socialSecurityNumber("6543217654321").build());

		// Beneficiary 생성
		Beneficiary beneficiary = Beneficiary.builder().name("박아무개").email("park001@test.com")
				.phoneNumber("01022223333").joinDate(LocalDate.of(2022, 10, 20)).socialSecurityNumber("1234561234567")
				.address("서울특별시 어딘가").insureds(insureds).identification(identification)
				.anotherSubscribes(anotherSubscribes).account(account).build();

		// Beneficiary 저장
		beneficiaryRepository.save(beneficiary);
		beneficiaryRepository.flush();
		
		// 회원 가입 전 beneficiary 조회
		Optional<Beneficiary> savedBeneficiary = beneficiaryRepository.findByEmail(inputEmail);
		
		// beneficiary가 있는 경우 member 저장 시 beneficiary로 저장 후 기존의 beneficiary와 연결, 없는 경우 member 저장 시 client로 저장
		if(savedBeneficiary.isPresent()) {
			
			Member member = Member.builder()
					.email(savedBeneficiary.get().getEmail())
					.role("beneficiary")
					.beneficiary(savedBeneficiary.get()).build();
			memberRepository.save(member);
			
			savedBeneficiary.get().setMember(member);
			
			memberRepository.flush();
			beneficiaryRepository.flush();
			
			assertEquals("beneficiary", memberRepository.findByEmail(inputEmail).get().getRole());
		} else {
			
			Member member = Member.builder()
					.email(inputEmail)
					.role("client")
					.build();
			memberRepository.save(member);
			
			memberRepository.flush();
			
			assertEquals("client", memberRepository.findByEmail(inputEmail).get().getRole());
		}
	}
	
	@Disabled
	@Test
	public void managerSignInTest() {
		
		String name = "박아무개";
		String email = "park001@test.com";
		String phoneNumber = "01012345678";
		
		Manager manager = Manager.builder()
				.name(name)
				.email(email)
				.phoneNumber(phoneNumber)
				.joinDate(LocalDate.now())
				.build();
		
		Member member = Member.builder()
				.email(email)
				.role("manager")
				.manager(manager)
				.build();
		
		manager.setMember(member);
		
		memberRepository.save(member);
		memberRepository.flush();
		
		Optional<Manager> savedManager = managerRepository.findByEmail(email);
		
		savedManager.ifPresent(mana -> System.out.println(mana.getMember()));
		assertEquals(savedManager.get().getName(), name);
	}
}
