package kr.pe.aichief.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Accident;

public interface AccidentRepository extends JpaRepository<Accident, Integer> {

	List<Accident> findAllByOrderByAccidentIdDesc();
	
	Optional<Accident> findByClaim_Contract_ContractId(int id);
}
