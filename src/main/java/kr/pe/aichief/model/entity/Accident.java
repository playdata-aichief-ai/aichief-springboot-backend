package kr.pe.aichief.model.entity;

import java.time.LocalDateTime;

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
public class Accident {
	
	@Id
	@Column(name = "accident_id")
	private int accidentId;
	
	@Column(name = "accident_datetime")
	private LocalDateTime dateTime;
	
	private String location;
	
	private String details;
	
	@Column(name = "disease_name")
	private String diseaseName;
}
