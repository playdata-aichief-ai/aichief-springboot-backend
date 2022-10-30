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
public class AnotherSubscribe {

	@Id
	@Column(name = "another_subscribe_id")
	private int anotherSubscribeId;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "subscribe_number")
	private int number;
}
