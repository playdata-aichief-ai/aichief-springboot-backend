package kr.pe.aichief.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {

	List<Claim> findByContract_Beneficiary_Email(String email);
	
	List<Claim> findByAssign_Manager_Email(String email);
}
