package kr.pe.aichief.model.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
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
public class Claim {
	
	@Id
	@Column(name = "claim_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int claimId;
	
	@Column(name = "reason_for_partial_claim")
	private String reasonForPartialClaim;
	
	@Column(name = "claim_date", nullable = false)
	private LocalDate date;
	
	@ToString.Exclude
	@OneToOne(mappedBy = "claim")
	private Contract contract;
	
	@ToString.Exclude
	@OneToOne(mappedBy = "claim", cascade = CascadeType.ALL)
	private Accident accident;
	
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "assign_id", unique = true)
	private Assign assign;
}
