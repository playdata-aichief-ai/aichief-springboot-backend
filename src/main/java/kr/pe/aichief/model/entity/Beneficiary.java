package kr.pe.aichief.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
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
@NamedEntityGraph(name = "Beneficiary.anotherSubscribes", attributeNodes = @NamedAttributeNode("anotherSubscribes"))
public class Beneficiary {
	
	@Id
	@Column(name = "beneficiary_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@ToString.Exclude
	@OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL)
	private List<Insured> insureds;
	
	@ToString.Exclude
	@OneToOne(mappedBy = "beneficiary", cascade = CascadeType.ALL)
	private Identification identification;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL)
	private List<AnotherSubscribe> anotherSubscribes;
	
	@ToString.Exclude
	@OneToOne(mappedBy = "beneficiary", cascade = CascadeType.ALL)
	private Account account;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "beneficiary")
	private List<Contract> contracts;
	
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "member_id", unique = true)
	private Member member;
}
