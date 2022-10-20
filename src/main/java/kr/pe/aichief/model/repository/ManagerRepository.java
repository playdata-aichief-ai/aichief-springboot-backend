package kr.pe.aichief.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.dao.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
	
	Manager findByEmail(String email);
}
