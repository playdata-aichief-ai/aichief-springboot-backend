package kr.pe.aichief.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import kr.pe.aichief.model.dto.ClientDTO;
import kr.pe.aichief.model.service.ClientService;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClientService clientService;
	
	@Test
	void searchClientInfoTest() throws Exception {
		
		ClientDTO client = ClientDTO.builder()
				.phoneNumber("01011112222")
				.name("아무개")
				.socialSecurityNumber("1234561234567")
				.address("서울특별시 어딘가")
				.nationality("kr")
				.build();
		
		Mockito.when(clientService.findByPhoneNumber(client.getPhoneNumber())).thenReturn(client);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/client").param("phone_number", "01011112222"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}
}
