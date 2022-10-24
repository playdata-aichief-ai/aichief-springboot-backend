package kr.pe.aichief.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@ToString

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Actor {
	
	@Id
	@Column(name = "actor_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int actorId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(name = "birth_day")
	private String birthDay;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "join_date")
	private String joinDate;
	
	@Column(nullable = false)
	private String role;
}
