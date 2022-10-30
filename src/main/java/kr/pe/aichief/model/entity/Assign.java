package kr.pe.aichief.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@Column(name = "assign_id")
	private int assignId;
	
	@ManyToOne(targetEntity = Manager.class)
	@JoinColumn(name = "manager_id")
	private Manager manager;
	
	@OneToOne(targetEntity = Claim.class)
	@JoinColumn(name = "claim_id")
	private Claim claim;
}
