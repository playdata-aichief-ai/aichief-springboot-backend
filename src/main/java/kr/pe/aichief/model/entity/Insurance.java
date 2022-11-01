package kr.pe.aichief.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Insurance {

	@Id
	@Column(name = "insurance_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int insuranceId;
	
	@Column(name = "company_name", nullable = false)
	private String companyName;
	
	private String details;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "insurance")
	private List<Contract> contracts;
}
