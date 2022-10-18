package kr.pe.aichief.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import kr.pe.aichief.model.dto.ClientDTO;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ClientRepositoryTest {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Test
	public void findByIdTest() throws Exception {
		
		ClientDTO client = ClientDTO.builder()
				.phoneNumber("01011112222")
				.name("아무개")
				.socialSecurityNumber("1234561234567")
				.address("서울특별시 어딘가")
				.nationality("kr")
				.build();
		
		ClientDTO result = ClientDTO.toClientDTO(clientRepository.findById(client.getPhoneNumber()).orElseThrow(() -> new EntityNotFoundException("Entiry Not Found: " + client.getPhoneNumber())));
		
		assertEquals(client.getName(), result.getName());
	}
}
