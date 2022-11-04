package kr.pe.aichief.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
	
	Optional<Manager> findByEmail(String email);
	
	@EntityGraph(value = "Manager.assigns")
	List<Manager> findAll();
}
