package kr.pe.aichief.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.pe.aichief.model.dto.AccidentDto;
import kr.pe.aichief.model.dto.AccountDto;
import kr.pe.aichief.model.dto.AnotherSubscribeDto;
import kr.pe.aichief.model.dto.BeneficiaryDto;
import kr.pe.aichief.model.dto.ClaimDto;
import kr.pe.aichief.model.dto.ClaimResult;
import kr.pe.aichief.model.dto.ContractDto;
import kr.pe.aichief.model.dto.ContractResult;
import kr.pe.aichief.model.dto.IdentificationDto;
import kr.pe.aichief.model.dto.InsuranceDto;
import kr.pe.aichief.model.dto.InsuredDto;
import kr.pe.aichief.model.entity.Claim;
import kr.pe.aichief.model.entity.Contract;
import kr.pe.aichief.model.repository.AccidentRepository;
import kr.pe.aichief.model.repository.AccountRepository;
import kr.pe.aichief.model.repository.AnotherSubscribeRepository;
import kr.pe.aichief.model.repository.BeneficiaryRepository;
import kr.pe.aichief.model.repository.ClaimRepository;
import kr.pe.aichief.model.repository.ContractRepository;
import kr.pe.aichief.model.repository.IdentificationRepository;
import kr.pe.aichief.model.repository.InsuranceRepository;
import kr.pe.aichief.model.repository.InsuredRepository;
import kr.pe.aichief.model.repository.ManagerRepository;
import kr.pe.aichief.util.DtoConverter;

@Disabled
@SpringBootTest
public class MyPageServiceTest {

	@Autowired
	private DtoConverter dtoConverter;

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private ClaimRepository claimRepository;

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
	private InsuranceRepository insuranceRepository;

	private String managerEmail = "kim@test.com";

	private String beneficiaryEmail = "park@test.com";

	// ??? ?????? ?????? - ?????????

	// ??? ?????? ?????? - ?????????
	@Disabled
	@Test
	void getAllBeneficiaryContractsTest() {

		String email = beneficiaryEmail;

		List<Contract> contracts = contractRepository.findByBeneficiary_Email(email);

		if (contracts.isEmpty()) {
			throw new EntityNotFoundException("Contracts Not Found");
		}

		List<ContractResult> contractResults = new ArrayList<ContractResult>();

		List<ContractDto> contractDtos = contracts.stream().map(contract -> dtoConverter.contractToDto(contract))
				.collect(Collectors.toList());

		for (ContractDto dto : contractDtos) {

			// ????????????
			InsuredDto insuredDto = dtoConverter.insuredToDto(
					insuredRepository.findByContracts_ContractId(Integer.parseInt(dto.getContractId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary's contracts: Insured not found")));

			// ?????????
			BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(
					beneficiaryRepository.findByContracts_ContractId(Integer.parseInt(dto.getContractId()))
							.orElseThrow(() -> new EntityNotFoundException(
									"Get all beneficiary's contracts: Beneficiary not found")));

			// ??????
			InsuranceDto insuranceDto = dtoConverter.insuranceToDto(
					insuranceRepository.findByContracts_ContractId(Integer.parseInt(dto.getContractId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary's contracts: Insurance not found")));

			contractResults.add(ContractResult.builder().contract(dto).insured(insuredDto).beneficiary(beneficiaryDto)
					.insurance(insuranceDto).build());
		}

		contractResults.forEach(cr -> System.out.println(cr));
	}

	// ??? ?????? ?????? - ?????????
	@Disabled
	@Test
	void getAllBeneficiaryClaimsTest() {

		String email = beneficiaryEmail;

		List<Claim> claims = claimRepository.findByContract_Beneficiary_Email(email);

		if (claims.isEmpty()) {
			throw new EntityNotFoundException("Claims Not Found");
		}

		List<ClaimResult> claimResults = new ArrayList<ClaimResult>();

		List<ClaimDto> claimDtos = claims.stream().map(claim -> dtoConverter.claimToDto(claim))
				.collect(Collectors.toList());
		for (ClaimDto dto : claimDtos) {

			// ??????
			ContractDto contractDto = dtoConverter.contractToDto(
					contractRepository.findByClaim_ClaimId(Integer.parseInt(dto.getClaimId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Contract not found")));

			// ??????
			AccidentDto accidentDto = dtoConverter.accidentToDto(
					accidentRepository.findByClaim_ClaimId(Integer.parseInt(dto.getClaimId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Accident not found")));

			// ????????????
			InsuredDto insuredDto = dtoConverter.insuredToDto(insuredRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Insured not found")));

			// ?????????
			BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(beneficiaryRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Beneficiary not found")));

			System.out.println(beneficiaryDto);

			// ?????????
			IdentificationDto identificationDto = dtoConverter.identificationToDto(identificationRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Identification not found")));

			// ?????? ??????
			AnotherSubscribeDto anotherSubscribeDto = dtoConverter.anotherSubscribeToDto(anotherSubscribeRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
					.orElseThrow(() -> new EntityNotFoundException(
							"Get all beneficiary claims: AnotherSubscribe not found")));

			// ??????
			AccountDto accountDto = dtoConverter.accountToDto(accountRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Account not found")));

			// ??????
			InsuranceDto insuranceDto = dtoConverter.insuranceToDto(insuranceRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Insurance not found")));

			claimResults.add(ClaimResult.builder().claim(dto).insured(insuredDto).beneficiary(beneficiaryDto)
					.identification(identificationDto).anotherSubscribe(anotherSubscribeDto).account(accountDto)
					.accident(accidentDto).contract(contractDto).insurance(insuranceDto).build());
		}

		System.out.println(claimResults);
	}

	// ??? ?????? ?????? - ?????????
	@Disabled
	@Test
	void getManagerTest() {
		System.out.println(dtoConverter.managerToDto(managerRepository.findByEmail(managerEmail).get()));
	}

	// ??? ?????? ?????? - ?????????

	// ??? ?????? ?????? - ?????????

	// ??? ?????? ?????? - ?????????
	@Disabled
	@Test
	void getAllManagerClaimsTest() {
		String email = managerEmail;

		List<Claim> claims = claimRepository.findByAssign_Manager_Email(email);

		if (claims.isEmpty()) {
			throw new EntityNotFoundException("Claims Not Found");
		}

		List<ClaimResult> claimResults = new ArrayList<ClaimResult>();

		List<ClaimDto> claimDtos = claims.stream().map(claim -> dtoConverter.claimToDto(claim))
				.collect(Collectors.toList());

		for (ClaimDto dto : claimDtos) {

			// ??????
			ContractDto contractDto = dtoConverter.contractToDto(
					contractRepository.findByClaim_ClaimId(Integer.parseInt(dto.getClaimId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Contract not found")));

			// ??????
			AccidentDto accidentDto = dtoConverter.accidentToDto(
					accidentRepository.findByClaim_ClaimId(Integer.parseInt(dto.getClaimId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Accident not found")));

			// ????????????
			InsuredDto insuredDto = dtoConverter.insuredToDto(insuredRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Insured not found")));

			// ?????????
			BeneficiaryDto beneficiaryDto = dtoConverter.beneficiaryToDto(beneficiaryRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Beneficiary not found")));

			// ?????????
			IdentificationDto identificationDto = dtoConverter.identificationToDto(identificationRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId())).orElseThrow(
							() -> new EntityNotFoundException("Get all beneficiary claims: Identification not found")));

			// ?????? ??????
			AnotherSubscribeDto anotherSubscribeDto = dtoConverter.anotherSubscribeToDto(anotherSubscribeRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
					.orElseThrow(() -> new EntityNotFoundException(
							"Get all beneficiary claims: AnotherSubscribe not found")));

			// ??????
			AccountDto accountDto = dtoConverter.accountToDto(accountRepository
					.findByBeneficiary_BeneficiaryId(Integer.parseInt(beneficiaryDto.getBeneficiaryId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Account not found")));

			// ??????
			InsuranceDto insuranceDto = dtoConverter.insuranceToDto(insuranceRepository
					.findByContracts_ContractId(Integer.parseInt(contractDto.getContractId()))
					.orElseThrow(() -> new EntityNotFoundException("Get all beneficiary claims: Insurance not found")));

			claimResults.add(ClaimResult.builder().claim(dto).insured(insuredDto).beneficiary(beneficiaryDto)
					.identification(identificationDto).anotherSubscribe(anotherSubscribeDto).account(accountDto)
					.accident(accidentDto).contract(contractDto).insurance(insuranceDto).build());
		}

		claimResults.forEach(cr -> System.out.println("?????? :" + cr));
	}

	// ?????? ????????? ???????????? ??????
	@Disabled
	@Test
	void getInsuredWithContractIdTest() {

		int contractId = 1;

		System.out.println(dtoConverter.insuredToDto(insuredRepository.findByContracts_ContractId(contractId)
				.orElseThrow(() -> new EntityNotFoundException("Get insured with contract id: Failed"))));
	}
}
