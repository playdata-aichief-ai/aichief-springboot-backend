package kr.pe.aichief.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {

}
