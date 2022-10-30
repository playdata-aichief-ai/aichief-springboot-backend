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
public class Insured {
	
	@Id
	@Column(name = "insured_id")
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
}
