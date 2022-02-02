package com.example.bancowdemo.adminuser.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.example.bancowdemo.TestSupport;

class ApiAdminUserControllerTest extends TestSupport {

	@Test
	void registerUser() throws Exception {
		mockMvc.perform(
			post("/api/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					"{\n"
						+ "  \"email\": \"gmldnr2222@naver.com\",\n"
						+ "  \"username\": \"김지훈\",\n"
						+ "  \"password\": \"passwordAndPassword2\",\n"
						+ "  \"password2\": \"passwordAndPassword2\"\n"
						+ "}"
				)
		).andExpect(status().isOk());
	}

	@Test
	void login() throws Exception {
		mockMvc.perform(
			post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					"{\n"
						+ "  \"email\": \"smtptestkk@gmail.com\",\n"
						+ "  \"password\": \"1111\"\n"
						+ "}"
				)
		).andExpect(status().isOk());
	}

	@Test
	void authentication() throws Exception {
	}

	@Test
	void findManager() throws Exception {
	}

	@Test
	void authenticationPassword() throws Exception {
	}

	@Test
	void changePassword() throws Exception {
	}

	@Test
	void statusToAdmin() throws Exception {
	}
}