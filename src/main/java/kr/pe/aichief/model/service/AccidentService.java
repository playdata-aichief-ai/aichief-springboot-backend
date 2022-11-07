package kr.pe.aichief.model.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import kr.pe.aichief.model.entity.Accident;
import kr.pe.aichief.model.repository.AccidentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccidentService {
	private final AccidentRepository accidentRepository;
	
	public Optional<Accident> getAccidentByClaimId(int claimId) throws JsonMappingException, JsonProcessingException {
		return accidentRepository.findByClaim_ClaimId(claimId);
	}

}
