package kr.pe.aichief.model.entity;

import java.time.LocalDate;

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
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString

@Entity
public class Claim {
	
	@Id
	@Column(name = "claim_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int claimId;
	
	@ManyToOne(targetEntity = Insured.class)
	@JoinColumn(name = "insured_id")
	private Insured insured;
	
	@ManyToOne(targetEntity = Beneficiary.class)
	@JoinColumn(name = "beneficiary_id")
	private Beneficiary beneficiary;
	
	@OneToOne(targetEntity = Accident.class)
	@JoinColumn(name = "accident_id")
	private Accident accident;
	
	@Column(name = "reason_for_partial_claim")
	private String reasonForPartialClaim;
	
	@Column(name = "claim_date")
	private LocalDate date;
}
