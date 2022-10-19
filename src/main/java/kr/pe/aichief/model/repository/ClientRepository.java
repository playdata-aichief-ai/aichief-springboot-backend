package kr.pe.aichief.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.pe.aichief.model.dao.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	Client findByNameAndEmail(String name, String email);
}
