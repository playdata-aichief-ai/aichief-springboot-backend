package kr.pe.aichief.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Identification {

	@Id
	@Column(name = "identification_id")
	private int identificationId;
	
	@Column(name = "id_number")
	private String number;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "issue_date")
	private LocalDate issueDate;
	
	@Column(name = "issue_by")
	private String issueBy;
}
