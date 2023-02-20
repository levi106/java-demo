package com.example.demo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DbController.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class DbControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void getReturnsOk() throws Exception {
		mockMvc.perform(get("/db"))
			   .andExpect(status().isOk());
	}

}
