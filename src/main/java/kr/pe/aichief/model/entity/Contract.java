package kr.pe.aichief.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString

@Entity
public class Contract {
	
	@Id
	@Column(name = "contract_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contractId;
	
	@Column(name = "monthly_premium")
	private float monthlyPremium;
	
	private String state;
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "insured_id")
	private Insured insured;
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "beneficiary_id")
	private Beneficiary beneficiary;
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "insurance_id")
	private Insurance insurance;
	
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "claim_id", unique = true)
	private Claim claim;
}
