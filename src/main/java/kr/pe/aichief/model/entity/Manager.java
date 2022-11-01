package kr.pe.aichief.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Manager {
	
	@Id
	@Column(name = "manager_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int managerId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	
	@Column(name = "join_date", nullable = false)
	private LocalDate joinDate;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "manager")
	private List<Assign> assigns;
	
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "member_id", unique = true)
	private Member member;
}
