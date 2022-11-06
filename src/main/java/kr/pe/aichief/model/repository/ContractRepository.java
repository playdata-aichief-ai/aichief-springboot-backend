package kr.pe.aichief.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

	List<Contract> findByBeneficiary_Email(String email);
	
	Optional<Contract> findByClaim_ClaimId(int id);
}
