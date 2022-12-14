package kr.pe.aichief.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Account {

	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;
	
	@Column(name = "bank_name", nullable = false)
	private String bankName;
	
	@Column(name = "account_number", nullable = false)
	private String number;
	
	@Column(name = "account_holder", nullable = false)
	private String holder;
	
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "beneficiary_id")
	private Beneficiary beneficiary;
}
