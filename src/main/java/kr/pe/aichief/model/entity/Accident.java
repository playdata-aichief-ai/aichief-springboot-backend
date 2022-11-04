package kr.pe.aichief.model.entity;

import java.time.LocalDateTime;

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
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString

@Entity
public class Accident {
	
	@Id
	@Column(name = "accident_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accidentId;
	
	@Column(name = "accident_datetime", nullable = false)
	private LocalDateTime dateTime;
	
	private String location;
	
	@Column(nullable = false)
	private String details;
	
	@Column(name = "disease_name")
	private String diseaseName;
	
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "claim_id", unique = true)
	private Claim claim;
}
