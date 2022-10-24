package kr.pe.aichief.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import kr.pe.aichief.model.service.ActorService;

@WebMvcTest(ActorController.class)
public class ActorControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ActorService clientService;
	
	@Test
	void searchClientInfoTest() throws Exception {
		
	}
}
