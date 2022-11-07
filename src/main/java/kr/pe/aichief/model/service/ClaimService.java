package kr.pe.aichief.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.pe.aichief.exceptions.InvalidInputException;
import kr.pe.aichief.model.dto.AccidentDto;
import kr.pe.aichief.model.dto.AccountDto;
import kr.pe.aichief.model.dto.AnotherSubscribeDto;
import kr.pe.aichief.model.dto.BeneficiaryDto;
import kr.pe.aichief.model.dto.ClaimDto;
import kr.pe.aichief.model.dto.ClaimPostRequest;
import kr.pe.aichief.model.dto.ClaimPutRequest;
import kr.pe.aichief.model.dto.ClaimResponse;
import kr.pe.aichief.model.dto.ClaimResult;
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
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClaimService {

	private final String requestBaseUrl = "http://3.34.231.50";

	private final String requestUrl = "/controller/get-information/";

	private final ObjectMapper mapper;

	private final MyConverter myConverter;

	private final DtoConverter dtoConverter;

	private final ManagerComparator managerComparator;

	private final ContractRepository contractRepository;

	private final ManagerRepository managerRepository;

	private final ClaimRepository claimRepository;

	private final AssignRepository assignRepository;

	private final BeneficiaryRepository beneficiaryRepository;

	private final IdentificationRepository identificationRepository;

	private final AnotherSubscribeRepository anotherSubscribeRepository;

	private final AccountRepository accountRepository;

	private final AccidentRepository accidentRepository;

	private final InsuredRepository insuredRepository;
	
	public ClaimResult serveClaim(ClaimPostRequest claimRequest) throws JsonMappingException, JsonProcessingException {

		// 1. API 요청

		// 1-1. WebClient 생성
		WebClient webClient = WebClient
				.builder()
				.baseUrl(requestBaseUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();

		// 1-2. 요청 및 응답
		ClaimResponse claimResponse = webClient.post()
				.uri(requestUrl)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(claimRequest), ClaimPostRequest.class)
				.retrieve()
				.bodyToMono(ClaimResponse.class)
				.share()
				.block();

		// 1-3. 인식 결과 객체로 변환
		RecognitionResult recognitionResult = mapper.readValue(claimResponse.getResult().replaceAll("\'", "\""), RecognitionResult.class);
		
		// 2. 새로운 데이터 저장 및 기존 데이터 업데이트

		// 2-1. identification, anotherSubscribe, account
		Beneficiary savedBeneficiary = beneficiaryRepository.findByEmail(claimRequest.getUser())
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

		Assign assign = Assign.builder().manager(savedManager).build();

		assignRepository.save(assign);

		// 2-3. claim
		List<Assign> savedAssigns = assignRepository.findAllByOrderByAssignIdDesc();
		Assign savedAssign = savedAssigns.get(0);

		Claim claim = Claim.builder().date(LocalDate.now()).assign(savedAssign).build();

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
		Contract savedContract = contractRepository.findById(claimRequest.getContract_id())
				.orElseThrow(() -> new EntityNotFoundException("Receive claim: Contract not found"));
		
		savedContract.setClaim(savedClaim);
		contractRepository.save(savedContract);
		
		// 3. 반환 객체 구성
		
		// 3-1. 청구
		ClaimDto claimDto = dtoConverter.claimToDto(claimRepository.findByContract_ContractId(claimRequest.getContract_id())
						.orElseThrow(() -> new EntityNotFoundException("Serve claim: Claim not found")));
				
		// 3-2. 피보험자
		InsuredDto insuredDto = dtoConverter.insuredToDto(insuredRepository.findByBeneficiary_Email(claimRequest.getUser())
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Insured not found")));

		// 3-3. 수익자
		BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(beneficiaryRepository.findByEmail(claimRequest.getUser())
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Beneficiary not found")));

		// 3-4. 신분증
		IdentificationDto identificationDto = dtoConverter.identificationToDto(identificationRepository
				.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: Identification not found")));

		// 3-5. 타사 가입
		AnotherSubscribeDto anotherSubscribeDto = dtoConverter.anotherSubscribeToDto(anotherSubscribeRepository
				.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
				.orElseThrow(() -> new EntityNotFoundException("Serve claim: AnotherSubscribe not found")));

		// 3-6. 계좌
		AccountDto accountDto = dtoConverter.accountToDto(
				accountRepository.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
						.orElseThrow(() -> new EntityNotFoundException("Serve claim: Account not found")));

		// 3-7. 사고
		AccidentDto accidentDto = dtoConverter.accidentToDto(accidentRepository.findByClaim_Contract_ContractId(claimRequest.getContract_id())
						.orElseThrow(() -> new EntityNotFoundException("Serve claim: Accident not found")));
		
		// 4. 반환
		return ClaimResult.builder()
				.claim(claimDto)
				.insured(insuredDto)
				.beneficiary(beneficiaryDto)
				.identification(identificationDto)
				.anotherSubscribe(anotherSubscribeDto)
				.account(accountDto)
				.accident(accidentDto)
				.build();
	}
	
	public void updateClaim(ClaimPutRequest claimRequest) 
			throws JsonMappingException, JsonProcessingException, InvalidInputException {
		
		// 1. 수정하려는 신분증, 타사가입, 계좌, 사고 조회
		
		// 1-1. 신분증
		Identification identificationBefore = identificationRepository.findByBeneficiary_Contracts_Claim_ClaimId(claimRequest.getClaimId())
				.orElseThrow(() -> new EntityNotFoundException("Update claim: Identification not found"));
		
		// 1-2. 타사가입
		AnotherSubscribe anotherSubscribeBefore = anotherSubscribeRepository.findByBeneficiary_Contracts_Claim_ClaimId(claimRequest.getClaimId())
				.orElseThrow(() -> new EntityNotFoundException("Update claim: AnotherSubscribe not found"));
		
		// 1-3. 계좌
		Account accountBefore = accountRepository.findByBeneficiary_Contracts_Claim_ClaimId(claimRequest.getClaimId())
				.orElseThrow(() -> new EntityNotFoundException("Update claim: Account not found"));
		
		// 1-4. 사고
		Accident accidentBefore = accidentRepository.findByClaim_ClaimId(claimRequest.getClaimId())
				.orElseThrow(() -> new EntityNotFoundException("Update claim: Accident not found"));
		
		// 2. 신분증, 타사가입, 계좌, 사고 수정
		
		// 2-1. 신분증
		identificationBefore.setNumber(claimRequest.getIdentification().getNumber());
		identificationBefore.setSerialNumber(claimRequest.getIdentification().getSerialNumber());
		identificationBefore.setIssueDate(LocalDate.parse(Optional.ofNullable(claimRequest.getIdentification().getIssueDate())
				.orElseThrow(() -> new InvalidInputException("Update claim: Value cant be null"))));
		identificationBefore.setIssueBy(claimRequest.getIdentification().getIssueBy());
		
		// 2-2. 타사가입
		anotherSubscribeBefore.setCompanyName(claimRequest.getAnotherSubscribe().getCompanyName());
		anotherSubscribeBefore.setNumber(Integer.parseInt(Optional.ofNullable(claimRequest.getAnotherSubscribe().getNumber())
				.orElseThrow(() -> new InvalidInputException("Update claim: Value cant be null"))));
		
		// 2-3. 계좌
		accountBefore.setBankName(claimRequest.getAccount().getBankName());
		accountBefore.setNumber(claimRequest.getAccount().getNumber());
		accountBefore.setHolder(claimRequest.getAccount().getHolder());
		
		// 2-4. 사고
		accidentBefore.setLocation(claimRequest.getAccident().getLocation());
		accidentBefore.setDetails(claimRequest.getAccident().getDetails());
		accidentBefore.setDiseaseName(claimRequest.getAccident().getDiseaseName());
		accidentBefore.setDateTime(LocalDateTime.parse(Optional.ofNullable(claimRequest.getAccident().getDateTime())
				.orElseThrow(() -> new InvalidInputException("Update claim: Value cant be null"))));
		
		// 3. 업데이트
		identificationRepository.save(identificationBefore);
		anotherSubscribeRepository.save(anotherSubscribeBefore);
		accountRepository.save(accountBefore);
		accidentRepository.save(accidentBefore);
	}
	
	public void deleteClaim(int id) {
		
		// 삭제 전 조회
		Beneficiary beneficiaryBefore = beneficiaryRepository.findByContracts_Claim_ClaimId(id)
				.orElseThrow(() -> new EntityNotFoundException("Delete claim: Beneficiary not found"));
		Identification identificationBefore = identificationRepository.findByBeneficiary_Contracts_Claim_ClaimId(id)
				.orElseThrow(() -> new EntityNotFoundException("Delete claim: Identification not found"));
		AnotherSubscribe anotherSubscribeBefore = anotherSubscribeRepository
				.findByBeneficiary_Contracts_Claim_ClaimId(id)
				.orElseThrow(() -> new EntityNotFoundException("Delete claim: AnotherSubscribe not found"));
		Account accountBefore = accountRepository.findByBeneficiary_Contracts_Claim_ClaimId(id)
				.orElseThrow(() -> new EntityNotFoundException("Delete claim: Account not found"));
		Assign assignBefore = assignRepository.findByClaim_ClaimId(id)
				.orElseThrow(() -> new EntityNotFoundException("Delete claim: Assign not found"));
		Claim claimBefore = claimRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Delete claim: Claim not found"));

		// 삭제
		beneficiaryBefore.setIdentification(null);
		beneficiaryBefore.setAccount(null);
		
		identificationRepository.delete((identificationBefore));
		anotherSubscribeRepository.delete(anotherSubscribeBefore);
		accountRepository.delete(accountBefore);
		assignRepository.delete(assignBefore);
		claimRepository.delete(claimBefore);
	}
}
