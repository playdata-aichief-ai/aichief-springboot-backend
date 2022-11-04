package kr.pe.aichief.model.dto;

import java.util.List;

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

public class RecognitionResult {

	private List<String> insuredName;
	
	private List<String> insuredSocialSecurityNumber;
	
	private List<String> insuredPhoneNumber;
	
	private List<String> insuredJob;
	
	private List<String> beneficiaryName;
	
	private List<String> beneficiarySocialSecurityNumber;
	
	private List<String> beneficiaryPhoneNumber;
	
	private List<String> beneficiaryEmail;
	
	private List<String> beneficiaryAddress;
	
	private List<String> identificationNumber;
	
	private List<String> identificationSerialNumber;
	
	private List<String> identificationIssueDate;
	
	private List<String> identificationIssueBy;
	
	private List<String> accidentDateTime;
	
	private List<String> accidentLocation;
	
	private List<String> accidentDetails;
	
	private List<String> accidentDiseaseName;
	
	private List<String> anotherSubscribeCompanyName;
	
	private List<String> anotherSubscribeNumber;
	
	private List<String> accountBankName;
	
	private List<String> accountNumber;
	
	private List<String> accountHolder;
}
