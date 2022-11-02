package kr.pe.aichief.model.entity;

import java.time.LocalDate;

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
public class Identification {

	@Id
	@Column(name = "identification_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int identificationId;
	
	@Column(name = "id_number", nullable = false, unique = true)
	private String number;
	
	@Column(name = "serial_number", unique = true)
	private String serialNumber;
	
	@Column(name = "issue_date")
	private LocalDate issueDate;
	
	@Column(name = "issue_by")
	private String issueBy;
	
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "beneficiary_id", unique = true)
	private Beneficiary beneficiary;
}
