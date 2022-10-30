package kr.pe.aichief.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
public class Beneficiary {
	
	@Id
	@Column(name = "beneficiary_id")
	private int beneficiaryId;
	
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
	
	private String landline;
	
	@Column(nullable = false)
	private String address;
	
	@Column(name = "relationship_with_insured")
	private String relationshipWithInsured;
	
	@OneToOne(targetEntity = Identification.class)
	@JoinColumn(name = "identification_id")
	private Identification identification;
	
	@OneToMany(targetEntity = AnotherSubscribe.class)
	@JoinColumn(name = "another_subscribe_id")
	private List<AnotherSubscribe> anotherSubscribes;
	
	@OneToOne(targetEntity = Account.class)
	@JoinColumn(name = "account_id")
	private Account account;
}
