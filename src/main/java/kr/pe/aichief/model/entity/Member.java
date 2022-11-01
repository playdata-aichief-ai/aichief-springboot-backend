package kr.pe.aichief.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Member {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memberId;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String role;
	
	private String state;
	
	@ToString.Exclude
	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
	private Manager manager;
	
	@ToString.Exclude
	@OneToOne(mappedBy = "member")
	private Beneficiary beneficiary;
}
