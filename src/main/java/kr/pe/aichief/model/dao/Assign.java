package kr.pe.aichief.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString

@Entity
public class Assign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "assign_id")
	private int assignId;
	
	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@OneToOne
	@JoinColumn(name = "manager_id")
	private Manager manager;
}
