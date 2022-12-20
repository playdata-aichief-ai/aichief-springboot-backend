# AI 계장님 - 보험금 청구서 텍스트 인식 웹 서비스

## Project Summary
![project_summary](https://user-images.githubusercontent.com/23309630/208612690-67a50495-c06e-4d7f-94b1-cc3434b8bb43.png)
**AI 계장님**은 보험금 청구서 접수 과정을 자동화하고 단순화해 사용자가 쉽고 편리하게 보험금을 청구할 수 있도록 개발한 웹 서비스입니다.

보험금 수익자가 보험금 청구서를 촬영해 업로드하면 텍스트 인식 인공지능 모델을 활용해 작성 내용을 인식하고 저장하며, 보험금 청구서를 처리할 담당자를 자동으로 배정합니다.

담당자는 배정받은 보험금 청구서 내용을 확인해 보험금을 수익자에게 지급합니다.

:link: [**시연 영상**](https://www.youtube.com/watch?v=-roHFd1IyiU)

팀원|담당|Github
---|---|---
박재민|Back-end|:link: [jacob3015](https://github.com/jacob3015)
이관용|Front-end|:link: [nailnoy](https://github.com/nailnoy)
임주완|Super resolution|:link: [dcafplz](https://github.com/dcafplz)
김정현|Area detection|:link: [Zolyer](https://github.com/Zolyer)
최영준|Text recognition|:link: [Choi-Korean](https://github.com/Choi-Korean)

---
## Tech Stack for back-end
<img src="https://img.shields.io/badge/java-F80000?style=for-the-badge&logo=oracle&logoColor=white">
<img src="https://img.shields.io/badge/spring_boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/amazon_ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
<img src="https://img.shields.io/badge/amazon_rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">
<img src="https://img.shields.io/badge/amazon_s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">

---
## Project Architecture
![proejct_architecture](https://user-images.githubusercontent.com/23309630/208628691-4ac830cc-f4e2-47a7-a829-d9894e0e7160.png)
- Business : 사용자가 보험금 청구서를 업로드하면 텍스트를 인식해 저장하고 담당자를 배정하는 것이 메인 서비스입니다.
- Application : Front-end, Back-end, 텍스트 인식 application을 각각 개발했습니다.
  - :link: [Front-end application repository](https://github.com/playdata-aichief-ai/aichief-nextjs-front)
  - :link: [Text recognition application repository](https://github.com/playdata-aichief-ai/aichief-python-aiserver)
- DB : AWS RDS를 활용해 MySQL을 사용합니다.
- Storage : AWS S3를 활용해 보험금 청구서 원본을 저장합니다.
- Infra : Back-end application과 텍스트 인식 application은 AWS EC2를 활용해 배포했으며, Front-end application은 netlify를 활용해 배포했습니다.

---
## Main Service Flow
![project_service_flow](https://user-images.githubusercontent.com/23309630/208632103-672673a9-4791-4ce0-a536-dd647840b09a.png)
1. 사용자는 보험금 청구서를 업로드합니다.
2. 보험금 청구서 작성 내용의 텍스트 인식을 요청합니다.
3. 보험금 청구서 작성 내용의 텍스트 영역을 각각의 이미지로 검출합니다.
4. 이미지를 초해상화합니다.
5. 이미지에서 텍스트를 인식합니다.
6. 인식 결과를 데이터베이스에 저장합니다.
7. 담당자를 배정합니다.
8. 사용자에게 인식 결과를 보여줍니다.
9. 사용자는 업로드한 보험금 청구서 작성 내용을 수정할 수 있으며, 업로드한 보험금 청구서를 삭제할 수 있습니다.

---
## DB Tables
![db_tables](https://user-images.githubusercontent.com/23309630/208632546-097d9c94-a88a-478f-a8d6-74d0fa954d98.png)

identification(신분증) table column name|설명
---|---
identification_id|PK
beneficiary_id|FK
id_number|주민 번호
serial_number|일련 번호
issue_date|발급 일
issue_by|발급 기관

another_subscribe(타사가입) table column name|설명
---|---
another_subscribe_id|PK
beneficiary_id|FK
company_name|보험사 명
subscribe_number|가입된 보험 수

account(계좌) table column name|설명
---|---
account_id|PK
beneficiary_id|FK
bank_name|은행 명
account_number|계좌 번호
account_holder|예금주

insured(피보험자) table column name|설명
---|---
insured_id|PK
beneficiary_id|FK
name|성명
email|이메일
phone_number|휴대 전화 번호
join_date|가입 일
social_security_number|주민 번호
job|직업

beneficiary(수익자) table column name|설명
---|---
beneficiary_id|PK
member_id|FK
name|성명
email|이메일
phone_number|휴대 전화 번호
join_date|가입 일
social_security_number|주민 번호
job|직업
landline|전화 번호
address|주소
relationship_with_insured|피보험자와의 관계

contract(계약) table column name|설명
---|---
contract_id|PK
insured_id|FK
beneficiary_id|FK
insurance_id|FK
claim_id|FK
monthly_premium|월 보험료
state|납입 상태

insurance(보험) table column name|설명
---|---
insurance_id|PK
company_name|보험사 명
details|보험 상세 설명

claim(보험금 청구서) table column name|설명
---|---
claim_id|PK
assign_id|FK
reason_for_partial_claim|일부 청구 희망 사유
claim_date|청구 일

accident(사고) table column name|설명
---|---
accident_id|PK
claim_id|FK
accident_datetime|사고 시간
location|사고 장소
details|사고 경위
disease_name|병명

assign(배정) table column name|설명
---|---
assign_id|PK
manager_id|FK

manager(담당자) table column name|설명
---|---
manager_id|PK
member_id|FK
name|성명
email|이메일
phone_number|휴대 전화 번호
join_date|입사 일

member(회원) table column name|설명
---|---
member_id|PK
email|이메일
role|역할
state|상태

---
## Environment
![environment](https://user-images.githubusercontent.com/23309630/208633105-2a7435a7-7777-4537-9515-322f7e5d4df4.png)
- VPC : 새로운 VPC를 생성해 사용했습니다.
- Subnet & Availability Zone & Internet gateway : 하나의 가용영역에 하나의 Subnet을 위치시켰으며, EC2 instance가 위치한 subnet은 새로운 routing table을 생성해 internet gateway로 향하는 routing을 추가했습니다.
- Application load balancer : HTTPS 적용을 위해 ALB를 생성했습니다. HTTPS 적용을 위해 AWS Route53에서 도메인을 구매했으며, AWS Certificate manager에서 SSL 인증서를 발급받았습니다. 구매한 도메인, 인증서 그리고 EC2 instance를 선택해 ALB를 생성했습니다.
- EC2 instance (1) : Java와 Spring boot를 활용해 개발한 back-end application 배포를 위해 사용했습니다.
- EC2 instance (2) : Python과 Django를 활용해 개발한 텍스트 인식 application 배포를 위해 사용했습니다.
- RDS instance : MySQL 데이터베이스를 사용하기 위해 생성했습니다.
- S3 bucket : 보험금 청구서 원본을 저장하기 위해 사용했습니다.
- netlify : TypeScript와 Next.js를 활용해 개발한 front-end application 배포를 위해 사용했습니다.

---
## Repository Summary
AI 계장님 back-end application source code repository 입니다.

---
## Configuration
```
📦 
├─ .gitignore
├─ .mvn
│  └─ wrapper
│     └─ maven-wrapper.properties
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ kr
   │  │     └─ pe
   │  │        └─ aichief
   │  │           ├─ AichiefSpringbootBackendApplication.java
   │  │           ├─ ServletInitializer.java
   │  │           ├─ config
   │  │           │  └─ WebConfig.java
   │  │           ├─ controller
   │  │           │  ├─ ClaimController.java
   │  │           │  ├─ ClientController.java
   │  │           │  ├─ ContractController.java
   │  │           │  ├─ HealthController.java
   │  │           │  ├─ InfoController.java
   │  │           │  └─ TestController.java
   │  │           ├─ exceptions
   │  │           │  ├─ InvalidInputException.java
   │  │           │  └─ MyExceptionHandler.java
   │  │           ├─ model
   │  │           │  ├─ dto
   │  │           │  │  ├─ AccidentDto.java
   │  │           │  │  ├─ AccountDto.java
   │  │           │  │  ├─ AnotherSubscribeDto.java
   │  │           │  │  ├─ AssignDto.java
   │  │           │  │  ├─ BeneficiaryDto.java
   │  │           │  │  ├─ ClaimDto.java
   │  │           │  │  ├─ ClaimPostRequest.java
   │  │           │  │  ├─ ClaimPutRequest.java
   │  │           │  │  ├─ ClaimResponse.java
   │  │           │  │  ├─ ClaimResult.java
   │  │           │  │  ├─ ContractDto.java
   │  │           │  │  ├─ ContractResult.java
   │  │           │  │  ├─ IdentificationDto.java
   │  │           │  │  ├─ InsuranceDto.java
   │  │           │  │  ├─ InsuredDto.java
   │  │           │  │  ├─ ManagerDto.java
   │  │           │  │  ├─ MyResponse.java
   │  │           │  │  └─ RecognitionResult.java
   │  │           │  ├─ entity
   │  │           │  │  ├─ Accident.java
   │  │           │  │  ├─ Account.java
   │  │           │  │  ├─ AnotherSubscribe.java
   │  │           │  │  ├─ Assign.java
   │  │           │  │  ├─ Beneficiary.java
   │  │           │  │  ├─ Claim.java
   │  │           │  │  ├─ Contract.java
   │  │           │  │  ├─ Identification.java
   │  │           │  │  ├─ Insurance.java
   │  │           │  │  ├─ Insured.java
   │  │           │  │  ├─ Manager.java
   │  │           │  │  └─ Member.java
   │  │           │  ├─ repository
   │  │           │  │  ├─ AccidentRepository.java
   │  │           │  │  ├─ AccountRepository.java
   │  │           │  │  ├─ AnotherSubscribeRepository.java
   │  │           │  │  ├─ AssignRepository.java
   │  │           │  │  ├─ BeneficiaryRepository.java
   │  │           │  │  ├─ ClaimRepository.java
   │  │           │  │  ├─ ContractRepository.java
   │  │           │  │  ├─ IdentificationRepository.java
   │  │           │  │  ├─ InsuranceRepository.java
   │  │           │  │  ├─ InsuredRepository.java
   │  │           │  │  ├─ ManagerRepository.java
   │  │           │  │  └─ MemberRepository.java
   │  │           │  └─ service
   │  │           │     ├─ ClaimService.java
   │  │           │     └─ MyPageService.java
   │  │           └─ util
   │  │              ├─ DtoConverter.java
   │  │              ├─ ManagerComparator.java
   │  │              └─ MyConverter.java
   │  └─ resources
   │     └─ application.properties
   └─ test
      └─ java
         └─ kr
            └─ pe
               └─ aichief
                  ├─ AichiefSpringbootBackendApplicationTests.java
                  ├─ model
                  │  └─ service
                  │     ├─ AuthServiceTest.java
                  │     ├─ ClaimServiceTest.java
                  │     └─ MyPageServiceTest.java
                  └─ util
                     ├─ DtoConverterTest.java
                     └─ ManagerComparatorTest.java
```
©generated by [Project Tree Generator](https://woochanleee.github.io/project-tree-generator)

---
## Features on back-end application
![application_features](https://user-images.githubusercontent.com/23309630/208639015-f1632a88-c1fe-41ef-98a3-b0d68b83eef3.png)

### 보험금 청구서 텍스트 인식 요청
```java
// src/main/java/kr/pe/aichief/controller/ClaimController.java

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
```
```java
// src/main/java/kr/pe/aichief/model/service/ClaimService.java

public ClaimResult serveClaim(ClaimPostRequest claimRequest) throws JsonMappingException, JsonProcessingException {

    // 1. 텍스트 인식 요청

    // 1-1. WebClinet 생성
	WebClient webClient = WebClient.builder()
		.baseUrl(requestBaseUrl)
		.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.build();

    // 1-2. POST 요청
	ClaimResponse claimResponse = webClient.post()
		.uri(requestUrl)
		.accept(MediaType.APPLICATION_JSON)
		.body(Mono.just(claimRequest), ClaimPostRequest.class)
		.retrieve()
		.bodyToMono(ClaimResponse.class)
		.share()
		.block();

    // 1-3. 인식 결과 dto 객체로 변환
	RecognitionResult recognitionResult = mapper.readValue(claimResponse.getResult().replaceAll("\'", "\""), 
        RecognitionResult.class);

    // 2. 인식 결과 저장 및 업데이트

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

	// 2-2. assign, 담당자 배정 - 배정된 청구서 수가 가장 적은 담당자를 찾아 배정함
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

    ...
}
```

### 보험금 청구서 수정
```java
// src/main/java/kr/pe/aichief/controller/ClaimController.java

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
```

```java
// src/main/java/kr/pe/aichief/model/service/ClaimService.java

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
```

### 보험금 청구서 삭제
```java
// src/main/java/kr/pe/aichief/controller/ClaimController.java

@DeleteMapping("/{claimId}")
public ResponseEntity<MyResponse> deleteClaim(@PathVariable("claimId") int claimId) {
		
	MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
		
	claimService.deleteClaim(claimId);
		
	result.setCode(HttpStatus.OK.value());
	result.setHttpStatus(HttpStatus.OK);
	result.setMessage("Request DELETE Claim Success");

	return ResponseEntity.status(result.getHttpStatus()).body(result);
}

```
```java
// src/main/java/kr/pe/aichief/model/service/ClaimService.java

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
```

### 개인 정보 조회
```java
// src/main/java/kr/pe/aichief/controller/InfoController.java

@GetMapping
public ResponseEntity<MyResponse> getInfo(@RequestParam("email") String email) {
		
	MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
	String role = myPageService.getMemberRole(email);

	if(role.equals("beneficiary")) {

        // 사용자가 수익자일 경우
		result.getContents().add(myPageService.getBeneficiary(email));

	} else {
        
        // 사용자가 담당자일 경우
		result.getContents().add(myPageService.getManager(email));

	}
		
	result.setCode(HttpStatus.OK.value());
	result.setHttpStatus(HttpStatus.OK);
	result.setCount(result.getContents().size());
	result.setMessage("Request get info success");
		
	return ResponseEntity.status(result.getHttpStatus()).body(result);
}
```

```java
// src/main/java/kr/pe/aichief/model/service/MyPageService.java

// 수익자 개인정보 조회
public BeneficiaryDto getBeneficiary(String email) {
		
	Beneficiary beneficiary = beneficiaryRepository.findByEmail(email)
		.orElseThrow(() -> new EntityNotFoundException("Beneficiary Not Found: " + email));
		
	BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(beneficiary);
		
	beneficiaryDto.setRole(memberRepository.findByEmail(email)
		.orElseThrow(() -> new EntityNotFoundException("Member Not Found")).getRole());
		
	return beneficiaryDto;
}
```

### 청구 내역 조회
```java
// src/main/java/kr/pe/aichief/controller/ClaimController.java

@GetMapping
public ResponseEntity<MyResponse> getAllClaims(@RequestParam("email") String email) {
		
	MyResponse result = MyResponse.builder().contents(new ArrayList<Object>()).build();
		
	String role = myPageService.getMemberRole(email);
		
	if(role.equals("beneficiary")) {

        // 사용자가 수익자일 경우
		result.setContents(myPageService.getAllBeneficiaryClaims(email).stream().map(claim -> (Object) claim).collect(Collectors.toList()));
	} else {

        // 사용자가 담당자일 경우
		result.setContents(myPageService.getAllManagerClaims(email).stream().map(claim -> (Object) claim).collect(Collectors.toList()));
	}
		
	result.setCode(HttpStatus.OK.value());
	result.setHttpStatus(HttpStatus.OK);
	result.setCount(result.getContents().size());
	result.setMessage("Request GET All Claims Success");
		
	return ResponseEntity.status(result.getHttpStatus()).body(result);
}
```

```java
// src/main/java/kr/pe/aichief/model/service/MyPageService.java

// 수익자 청구 내역 조회
public List<ClaimResult> getAllBeneficiaryClaims(String email) {
		
	List<Claim> claims = claimRepository.findByContract_Beneficiary_Email(email);
		
	if(claims.isEmpty()) {
		throw new EntityNotFoundException("Claims Not Found");
	}
		
	List<ClaimResult> claimResults = new ArrayList<ClaimResult>();
		
	List<ClaimDto> claimDtos = claims.stream().map(claim -> dtoConverter.claimToDto(claim)).collect(Collectors.toList());
	for(ClaimDto dto : claimDtos) {
			
		// 계약
		ContractDto contractDto = dtoConverter.contractToDto(
			contractRepository.findByClaim_ClaimId(Integer.parseInt(dto.getClaimId()))
            .orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Contract not found")));

		// 사고
		AccidentDto accidentDto = dtoConverter.accidentToDto(
			accidentRepository.findByClaim_ClaimId(Integer.parseInt(dto.getClaimId()))
            .orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Accident not found")));

		// 피보험자
		InsuredDto insuredDto = dtoConverter.insuredToDto(
            insuredRepository.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
			.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Insured not found")));

		// 수익자
		BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(
            beneficiaryRepository.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
            .orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Beneficiary not found")));

		// 신분증
		IdentificationDto identificationDto = dtoConverter.identificationToDto(
            identificationRepository.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
            .orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Identification not found")));

		// 타사 가입
		AnotherSubscribeDto anotherSubscribeDto = dtoConverter.anotherSubscribeToDto(
            anotherSubscribeRepository.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
			.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: AnotherSubscribe not found")));

		// 계좌
		AccountDto accountDto = dtoConverter.accountToDto(
            accountRepository.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
			.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Account not found")));

		// 보험
		InsuranceDto insuranceDto = dtoConverter.insuranceToDto(
            insuranceRepository.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
			.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Insurance not found")));

		claimResults.add(ClaimResult.builder()
			.claim(dto)
			.insured(insuredDto)
			.beneficiary(beneficiaryDto)
			.identification(identificationDto)
			.anotherSubscribe(anotherSubscribeDto)
			.account(accountDto)
			.accident(accidentDto)
			.contract(contractDto)
			.insurance(insuranceDto)
			.build());
	}
		
	return claimResults;
}
```