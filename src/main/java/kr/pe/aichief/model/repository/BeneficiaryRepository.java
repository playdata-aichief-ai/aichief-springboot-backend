package kr.pe.aichief.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {

	Optional<Beneficiary> findByEmail(String email);
	
	List<Beneficiary> findByName(String name);
	
	@EntityGraph(value = "Beneficiary.anotherSubscribes")
	Optional<Beneficiary> findByBeneficiaryId(int id);
	
	Optional<Beneficiary> findByContracts_ContractId(int id);
	
	Optional<Beneficiary> findByContracts_Claim_ClaimId(int id);
}
