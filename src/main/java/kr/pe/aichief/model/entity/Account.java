package kr.pe.aichief.model.entity;

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
public class Account {

	@Id
	@Column(name = "account_id")
	private int accountId;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "account_number")
	private String number;
	
	@Column(name = "account_holder")
	private String holder;
}
