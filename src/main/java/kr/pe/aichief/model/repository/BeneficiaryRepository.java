package kr.pe.aichief.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {

	Optional<Beneficiary> findByEmail(String email);
}
