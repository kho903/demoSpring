package com.example.bancowdemo.qna.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
class QnaApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(
            final WebApplicationContext context,
            final RestDocumentationContextProvider provider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .build();
    }

    @Test
    void getQna() throws Exception {
        mockMvc.perform(
                get("/api/qna/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"))
                .andExpect(status().isOk())
        ;
    }

    @Test
    void getAllQna() throws Exception {
        mockMvc.perform(
                get("/api/qna/allqna")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"))
                .andExpect(status().isOk())
        ;
    }

    @Test
    void addQna() throws Exception {
        mockMvc.perform(
                post("/api/qna/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("" +
                                "{\n" +
                                "  \"category\": \"농가입점\",\n" +
                                "  \"phoneNumber\": \"010-1010-0101\",\n" +
                                "  \"email\": \"a@naver.com\",\n" +
                                "  \"title\": \"아아\",\n" +
                                "  \"message\": \"아아\",\n" +
                                "  \"checked\": true\n" +
                                "}")
        )
                .andDo(print())
                .andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteQna() throws Exception {
        mockMvc.perform(
                delete("/api/qna/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"))
                .andExpect(status().isOk());
    }
}