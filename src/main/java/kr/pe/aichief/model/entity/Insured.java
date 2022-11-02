package kr.pe.aichief.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
public class Insured {
	
	@Id
	@Column(name = "insured_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int insuredId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	
	@Column(name = "join_date", nullable = false)
	private LocalDate joinDate;
	
	@Column(name = "social_security_number", nullable = false)
	private String socialSecurityNumber;
	
	private String job;
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "beneficiary_id")
	private Beneficiary beneficiary;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "insured")
	private List<Contract> contracts;
}
