package kr.pe.aichief.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	Optional<Account> findByBeneficiary_BeneficiaryId(int id);
	
	Optional<Account> findByBeneficiary_Contracts_Claim_ClaimId(int id);
	
	List<Account> findAllByOrderByAccountIdDesc();
}
