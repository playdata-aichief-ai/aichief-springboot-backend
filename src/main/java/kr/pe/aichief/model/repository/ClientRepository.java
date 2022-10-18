package kr.pe.aichief.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.aichief.model.dao.Client;

public interface ClientRepository extends JpaRepository<Client, String> {

}
