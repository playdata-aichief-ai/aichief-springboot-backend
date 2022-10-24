package kr.pe.aichief.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.dao.Assign;

public interface AssignRepository extends JpaRepository<Assign, Integer> {

	public List<Assign> findAssignByManager_Email(String email);
}
