package kr.pe.aichief.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.AnotherSubscribe;

public interface AnotherSubscribeRepository extends JpaRepository<AnotherSubscribe, Integer> {

	Optional<AnotherSubscribe> findByBeneficiary_BeneficiaryId(int id);
	
	Optional<AnotherSubscribe> findByBeneficiary_Contracts_Claim_ClaimId(int id);
	
	List<AnotherSubscribe> findAllByOrderByAnotherSubscribeIdDesc();
}
