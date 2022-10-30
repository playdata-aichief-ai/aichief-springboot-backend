package kr.pe.aichief.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
	
	Optional<Manager> findByEmail(String email);
}
