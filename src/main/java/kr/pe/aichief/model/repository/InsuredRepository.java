package kr.pe.aichief.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Insured;

public interface InsuredRepository extends JpaRepository<Insured, Integer> {
	
	Optional<Insured> findByEmail(String email);
	
	Optional<Insured> findByBeneficiary_Email(String email);
	
	Optional<Insured> findByContracts_ContractId(int id);
	
	Optional<Insured> findByContracts_Claim_ClaimId(int id);
}
