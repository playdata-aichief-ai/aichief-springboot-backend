package kr.pe.aichief.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.pe.aichief.model.dto.AccidentDto;
import kr.pe.aichief.model.dto.AccountDto;
import kr.pe.aichief.model.dto.AnotherSubscribeDto;
import kr.pe.aichief.model.dto.BeneficiaryDto;
import kr.pe.aichief.model.dto.ClaimDto;
import kr.pe.aichief.model.dto.ClaimRequest;
import kr.pe.aichief.model.dto.ClaimResponse;
import kr.pe.aichief.model.dto.IdentificationDto;
import kr.pe.aichief.model.dto.InsuredDto;
import kr.pe.aichief.model.dto.RecognitionResult;
import kr.pe.aichief.model.entity.Accident;
import kr.pe.aichief.model.entity.Account;
import kr.pe.aichief.model.entity.AnotherSubscribe;
import kr.pe.aichief.model.entity.Assign;
import kr.pe.aichief.model.entity.Beneficiary;
import kr.pe.aichief.model.entity.Claim;
import kr.pe.aichief.model.entity.Contract;
import kr.pe.aichief.model.entity.Identification;
import kr.pe.aichief.model.entity.Manager;
import kr.pe.aichief.model.repository.AccidentRepository;
import kr.pe.aichief.model.repository.AccountRepository;
import kr.pe.aichief.model.repository.AnotherSubscribeRepository;
import kr.pe.aichief.model.repository.AssignRepository;
import kr.pe.aichief.model.repository.BeneficiaryRepository;
import kr.pe.aichief.model.repository.ClaimRepository;
import kr.pe.aichief.model.repository.ContractRepository;
import kr.pe.aichief.model.repository.IdentificationRepository;
import kr.pe.aichief.model.repository.InsuredRepository;
import kr.pe.aichief.model.repository.ManagerRepository;
import kr.pe.aichief.util.DtoConverter;
import kr.pe.aichief.util.ManagerComparator;
import kr.pe.aichief.util.MyConverter;
import reactor.core.publisher.Mono;

@Disabled
@SpringBootTest
public class ClaimServiceTest {

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private AssignRepository assignRepository;

	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private IdentificationRepository identificationRepository;
	
	@Autowired
	private AnotherSubscribeRepository anotherSubscribeRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccidentRepository accidentRepository;
	
	@Autowired
	private InsuredRepository insuredRepository;

	@Autowired
	private MyConverter myConverter;

	@Autowired
	private DtoConverter dtoConverter;

	@Autowired
	private ManagerComparator managerComparator;

	@Disabled
	@Test
	void callExternalApiTest() throws Exception {

		// 필요한 변수 초기화
		Logger logger = LoggerFactory.getLogger(ClaimServiceTest.class);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String requestBaseUrl = "http://3.34.231.50";
		String requestUrl = "/controller/get-information/";

		String testUser = "kang@test.com";
		int testContractId = 2;
		String testCompanyName = "samsung";
		String testImagePath = "projects/images/samsung-11.jpg";

		// 1. 외부 API에 요청 - 응답

		// WebClinet 생성
		WebClient webClient = WebClient.builder().baseUrl(requestBaseUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		// Request 생성
		ClaimRequest claimRequest = ClaimRequest.builder().user(testUser).contract_id(testContractId)
				.company(testCompanyName).image_path(testImagePath).build();

		// 응답 받기
		ClaimResponse claimResponse = webClient.post().uri(requestUrl).accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(claimRequest), ClaimRequest.class).retrieve().bodyToMono(ClaimResponse.class).share()
				.block();

		// 응답에서 인식 결과 객체로 변환
		ObjectMapper mapper = new ObjectMapper();
		RecognitionResult recognitionResult = mapper.readValue(claimResponse.getResult().replaceAll("\'", "\""),
				RecognitionResult.class);

		logger.info("응답 결과: " + claimResponse.toString());
		logger.info("인식 결과: " + claimResponse.getResult());
		logger.info("Json to Object 변환 결과: " + recognitionResult.toString());

		// 2. 새로운 데이터 저장 및 기존 데이터 업데이트
		
		// 2-1. identification, anotherSubscribe, account
		Beneficiary savedBeneficiary = beneficiaryRepository.findByEmail(testUser)
				.orElseThrow(() -> new EntityNotFoundException("Receive claim: Beneficiary not found"));

		Identification identification = Identification.builder()
				.number(myConverter.spaceJoiner(recognitionResult.getIdentificationNumber()))
				.serialNumber(myConverter.spaceJoiner(recognitionResult.getIdentificationSerialNumber()))
				.issueBy(myConverter.spaceJoiner(recognitionResult.getIdentificationIssueBy()))
				.issueDate(LocalDate.now())
				.beneficiary(savedBeneficiary)
				.build();

		AnotherSubscribe anotherSubscribe = AnotherSubscribe.builder()
				.companyName(myConverter.spaceJoiner(recognitionResult.getAnotherSubscribeCompanyName()))
				.number(0)
				.beneficiary(savedBeneficiary)
				.build();

		Account account = Account.builder()
				.bankName(myConverter.spaceJoiner(recognitionResult.getAccountBankName()))
				.number(myConverter.spaceJoiner(recognitionResult.getAccountNumber()))
				.holder(myConverter.spaceJoiner(recognitionResult.getAccountHolder()))
				.beneficiary(savedBeneficiary)
				.build();
		
		identificationRepository.save(identification);
		anotherSubscribeRepository.save(anotherSubscribe);
		accountRepository.save(account);
		
		// 2-2. assign
		List<Manager> savedManagers = managerRepository.findAll();
		Collections.sort(savedManagers, managerComparator);
		Manager savedManager = savedManagers.get(0);

		Assign assign = Assign.builder()
				.manager(savedManager)
				.build();
		
		assignRepository.save(assign);
		
		// 2-3. claim
		List<Assign> savedAssigns = assignRepository.findAllByOrderByAssignIdDesc();
		Assign savedAssign = savedAssigns.get(0);
		
		Claim claim = Claim.builder()
				.date(LocalDate.now())
				.assign(savedAssign)
				.build();
		
		claimRepository.save(claim);
		
		// 2-4. accident
		List<Claim> savedClaims = claimRepository.findAllByOrderByClaimIdDesc();
		Claim savedClaim = savedClaims.get(0);
		
		Accident accident = Accident.builder()
				.location(myConverter.spaceJoiner(recognitionResult.getAccidentLocation()))
				.details(myConverter.spaceJoiner(recognitionResult.getAccidentDetails()))
				.diseaseName(myConverter.spaceJoiner(recognitionResult.getAccidentDiseaseName()))
				.dateTime(LocalDateTime.now())
				.claim(savedClaim)
				.build();
		
		accidentRepository.save(accident);
		
		// 2-5. contract
		Contract savedContract = contractRepository.findById(testContractId)
				.orElseThrow(() -> new EntityNotFoundException("Receive claim: Contract not found"));
		savedContract.setClaim(savedClaim);
		contractRepository.save(savedContract);
		
		stopWatch.stop();

		logger.info("최종 처리 시간: " + stopWatch.getTotalTimeSeconds());
	}
	
	@Disabled
	@Test
	void serveClaimTest() {
		
		// given
		String testUser = "kang@test.com";
		int testContractId = 2;
		
		// 청구
		ClaimDto claimDto = dtoConverter.claimToDto(claimRepository.findByContract_ContractId(testContractId)
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Claim not found")));
		
		// 피보험자
		InsuredDto insuredDto = dtoConverter.insuredToDto(insuredRepository.findByBeneficiary_Email(testUser)
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Insured not found")));
		
		// 수익자
		BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(beneficiaryRepository.findByEmail(testUser)
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Beneficiary not found")));
		
		// 신분증
		IdentificationDto identificationDto = dtoConverter.identificationToDto(identificationRepository
						.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
						.orElseThrow(() -> new EntityNotFoundException("Serve claim: Identification not found")));
		
		// 타사가입
		AnotherSubscribeDto anotherSubscribeDto = dtoConverter.anotherSubscribeToDto(anotherSubscribeRepository
				.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: AnotherSubscribe not found")));
		
		// 계좌
		AccountDto accountDto = dtoConverter.accountToDto(accountRepository
				.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Account not found")));
		
		// 사고
		AccidentDto accidentDto = dtoConverter.accidentToDto(accidentRepository.findByClaim_Contract_ContractId(testContractId)
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Accident not found")));
		
		System.out.println(claimDto);
		System.out.println(insuredDto);
		System.out.println(beneficiaryDto);
		System.out.println(identificationDto);
		System.out.println(anotherSubscribeDto);
		System.out.println(accountDto);
		System.out.println(accidentDto);
		
	}
}
