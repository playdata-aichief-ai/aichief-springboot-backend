package kr.pe.aichief.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

	Optional<Insurance> findByContracts_ContractId(int id);
}
