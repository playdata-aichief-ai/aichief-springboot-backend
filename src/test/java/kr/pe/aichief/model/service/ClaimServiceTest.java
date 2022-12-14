package kr.pe.aichief.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

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
import kr.pe.aichief.model.dto.AssignDto;
import kr.pe.aichief.model.dto.BeneficiaryDto;
import kr.pe.aichief.model.dto.ClaimDto;
import kr.pe.aichief.model.dto.ClaimPostRequest;
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

	// ????????? ?????? API ?????? ??? ?????? ?????????
	@Disabled
	@Test
	void callExternalApiTest() throws Exception {

		// ????????? ?????? ?????????
		Logger logger = LoggerFactory.getLogger(ClaimServiceTest.class);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String requestBaseUrl = "http://3.34.231.50";
		String requestUrl = "/controller/get-information/";

		String testUser = "kang@test.com";
		int testContractId = 2;
		String testCompanyName = "samsung";
		String testImagePath = "projects/images/samsung-11.jpg";

		// 1. ?????? API??? ?????? - ??????

		// WebClinet ??????
		WebClient webClient = WebClient.builder().baseUrl(requestBaseUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		// Request ??????
		ClaimPostRequest claimRequest = ClaimPostRequest.builder().user(testUser).contract_id(testContractId)
				.company(testCompanyName).image_path(testImagePath).build();

		// ?????? ??????
		ClaimResponse claimResponse = webClient.post().uri(requestUrl).accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(claimRequest), ClaimPostRequest.class).retrieve().bodyToMono(ClaimResponse.class).share()
				.block();

		// ???????????? ?????? ?????? ????????? ??????
		ObjectMapper mapper = new ObjectMapper();
		RecognitionResult recognitionResult = mapper.readValue(claimResponse.getResult().replaceAll("\'", "\""),
				RecognitionResult.class);

		logger.info("?????? ??????: " + claimResponse.toString());
		logger.info("?????? ??????: " + claimResponse.getResult());
		logger.info("Json to Object ?????? ??????: " + recognitionResult.toString());

		// 2. ????????? ????????? ?????? ??? ?????? ????????? ????????????
		
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

		logger.info("?????? ?????? ??????: " + stopWatch.getTotalTimeSeconds());
	}
	
	// ?????? ?????? ?????? ??? ?????? ?????? ?????????
	@Disabled
	@Test
	void serveClaimTest() {
		
		// given
		String testUser = "kang@test.com";
		int testContractId = 2;
		
		// ??????
		ClaimDto claimDto = dtoConverter.claimToDto(claimRepository.findByContract_ContractId(testContractId)
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Claim not found")));
		
		// ????????????
		InsuredDto insuredDto = dtoConverter.insuredToDto(insuredRepository.findByBeneficiary_Email(testUser)
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Insured not found")));
		
		// ?????????
		BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(beneficiaryRepository.findByEmail(testUser)
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Beneficiary not found")));
		
		// ?????????
		IdentificationDto identificationDto = dtoConverter.identificationToDto(identificationRepository
						.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
						.orElseThrow(() -> new EntityNotFoundException("Serve claim: Identification not found")));
		
		// ????????????
		AnotherSubscribeDto anotherSubscribeDto = dtoConverter.anotherSubscribeToDto(anotherSubscribeRepository
				.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: AnotherSubscribe not found")));
		
		// ??????
		AccountDto accountDto = dtoConverter.accountToDto(accountRepository
				.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Account not found")));
		
		// ??????
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
	
	// ?????? ?????? ?????????
	@Disabled
	@Test
	@Transactional
	void deleteClaimTest() {
		
		int id = 19;
		
		// ?????? ??? ?????? ????????? ?????? ??????
		List<AssignDto> assignDtosBefore = assignRepository.findAll().stream().map(assign -> dtoConverter.assignToDto(assign)).collect(Collectors.toList());
		List<AccidentDto> accidentDtosBefore = accidentRepository.findAll().stream().map(acc -> dtoConverter.accidentToDto(acc)).collect(Collectors.toList());
		List<ClaimDto> claimDtosBefore = claimRepository.findAll().stream().map(claim -> dtoConverter.claimToDto(claim)).collect(Collectors.toList());
		List<IdentificationDto> identificationDtosBefore = identificationRepository.findAll().stream().map(ide -> dtoConverter.identificationToDto(ide)).collect(Collectors.toList());
		List<AnotherSubscribeDto> anotherSubscribeDtosBefore = anotherSubscribeRepository.findAll().stream().map(as -> dtoConverter.anotherSubscribeToDto(as)).collect(Collectors.toList());
		List<AccountDto> accountDtosBefore = accountRepository.findAll().stream().map(acc -> dtoConverter.accountToDto(acc)).collect(Collectors.toList());
		
		assignDtosBefore.forEach(ass -> System.out.println(ass));
		accidentDtosBefore.forEach(acc -> System.out.println(acc));
		claimDtosBefore.forEach(cla -> System.out.println(cla));
		identificationDtosBefore.forEach(ide -> System.out.println(ide));
		anotherSubscribeDtosBefore.forEach(as -> System.out.println(as));
		accountDtosBefore.forEach(acc -> System.out.println(acc));
		
		// ?????? ??? ??????
		Beneficiary beneficiaryBefore = beneficiaryRepository.findByContracts_Claim_ClaimId(id)
				.orElseThrow(() -> new EntityNotFoundException("Delete claim test: Beneficiary not found"));
		Identification identificationBefore = identificationRepository.findByBeneficiary_Contracts_Claim_ClaimId(id)
				.orElseThrow(() -> new EntityNotFoundException("Delete claim test: Identification not found"));
		AnotherSubscribe anotherSubscribeBefore = anotherSubscribeRepository.findByBeneficiary_Contracts_Claim_ClaimId(id)
				.orElseThrow(() -> new EntityNotFoundException("Delete claim test: AnotherSubscribe not found"));
		Account accountBefore = accountRepository.findByBeneficiary_Contracts_Claim_ClaimId(id)
				.orElseThrow(() -> new EntityNotFoundException("Delete claim test: Account not found"));
		Assign assignBefore = assignRepository.findByClaim_ClaimId(id).orElseThrow(() -> new EntityNotFoundException("Delete claim test: Assign not found"));
		Claim claimBefore = claimRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Delete claim test: Claim not found"));
		
		// ??????
		beneficiaryBefore.setIdentification(null);
		beneficiaryBefore.setAccount(null);
		
		identificationRepository.delete(identificationBefore);
		anotherSubscribeRepository.delete(anotherSubscribeBefore);
		accountRepository.delete(accountBefore);
		assignRepository.delete(assignBefore);
		claimRepository.delete(claimBefore);
		
		// ?????? ??? ??????
		Optional<Identification> identificationAfter = identificationRepository.findByBeneficiary_Contracts_Claim_ClaimId(id);
		Optional<AnotherSubscribe> anotherSubscribeAfter = anotherSubscribeRepository.findByBeneficiary_Contracts_Claim_ClaimId(id);
		Optional<Account> accountAfter = accountRepository.findByBeneficiary_Contracts_Claim_ClaimId(id);
		Optional<Assign> assignAfter = assignRepository.findByClaim_ClaimId(id);
		Optional<Claim> claimAfter = claimRepository.findById(id);
		
		// ?????? ??????
		assertEquals(true, identificationAfter.isEmpty());
		assertEquals(true, anotherSubscribeAfter.isEmpty());
		assertEquals(true, accountAfter.isEmpty());
		assertEquals(true, assignAfter.isEmpty());
		assertEquals(true, claimAfter.isEmpty());
		
		// ?????? ??? ?????? ????????? ?????? ??????
		List<IdentificationDto> identificationDtosAfter = identificationRepository.findAll().stream()
				.map(ide -> dtoConverter.identificationToDto(ide)).collect(Collectors.toList());
		List<AnotherSubscribeDto> anotherSubscribeDtosAfter = anotherSubscribeRepository.findAll().stream()
				.map(as -> dtoConverter.anotherSubscribeToDto(as)).collect(Collectors.toList());
		List<AccountDto> accountDtosAfter = accountRepository.findAll().stream()
				.map(acc -> dtoConverter.accountToDto(acc)).collect(Collectors.toList());
		List<AssignDto> assignDtosAfter = assignRepository.findAll().stream().map(assign -> dtoConverter.assignToDto(assign))
				.collect(Collectors.toList());
		List<AccidentDto> accidentDtosAfter = accidentRepository.findAll().stream()
				.map(acc -> dtoConverter.accidentToDto(acc)).collect(Collectors.toList());
		List<ClaimDto> claimDtosAfter = claimRepository.findAll().stream().map(claim -> dtoConverter.claimToDto(claim))
				.collect(Collectors.toList());

		assignDtosAfter.forEach(ass -> System.out.println(ass));
		accidentDtosAfter.forEach(acc -> System.out.println(acc));
		claimDtosAfter.forEach(cla -> System.out.println(cla));
		identificationDtosAfter.forEach(ide -> System.out.println(ide));
		anotherSubscribeDtosAfter.forEach(as -> System.out.println(as));
		accountDtosAfter.forEach(acc -> System.out.println(acc));
	}
}
