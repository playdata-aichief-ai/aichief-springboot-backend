package kr.pe.aichief.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Assign;

public interface AssignRepository extends JpaRepository<Assign, Integer> {

	List<Assign> findByManager_Email(String email);
	
	List<Assign> findAllByOrderByAssignIdDesc();
	
	Optional<Assign> findByClaim_ClaimId(int id);
}
