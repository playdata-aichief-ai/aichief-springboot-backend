package kr.pe.aichief.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Identification;

public interface IdentificationRepository extends JpaRepository<Identification, Integer> {

	Optional<Identification> findByBeneficiary_BeneficiaryId(int id);
	
	List<Identification> findAllByOrderByIdentificationIdDesc();
}
