package com.example.bancowdemo.adminuser.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.example.bancowdemo.TestSupport;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiAdminUserControllerTest extends TestSupport {

	@Test
	void registerUser() throws Exception {
		mockMvc.perform(
			post("/api/register", 1L)
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