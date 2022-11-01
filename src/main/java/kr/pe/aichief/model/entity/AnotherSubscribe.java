package kr.pe.aichief.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class AnotherSubscribe {

	@Id
	@Column(name = "another_subscribe_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int anotherSubscribeId;
	
	@Column(name = "company_name", nullable = false)
	private String companyName;
	
	@Column(name = "subscribe_number")
	private int number;
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "beneficiary_id")
	private Beneficiary beneficiary;
}
