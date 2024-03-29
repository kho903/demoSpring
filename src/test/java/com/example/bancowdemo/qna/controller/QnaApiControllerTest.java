package com.example.bancowdemo.qna.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.example.bancowdemo.TestSupport;
import com.example.bancowdemo.adminuser.model.UserLoginInput;
import com.example.bancowdemo.adminuser.service.ApiAdminUserService;
import com.example.bancowdemo.util.token.entity.Token;
import com.example.bancowdemo.util.token.repository.TokenRepository;

class QnaApiControllerTest extends TestSupport {

    @Autowired
    private ApiAdminUserService apiAdminUserService;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    void userSet() {
        apiAdminUserService.loginUser(
            new UserLoginInput("smtptestkk@gmail.com", "1111")
        );
    }

    @Test
    void getQna() throws Exception {
        Token token = tokenRepository.findByUserId(1L).get();
        mockMvc.perform(
            get("/api/qna/{id}", 1L)
                .header("TOKEN", token.getToken())
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("id").description("QnA의 id")
                                ),
                                responseFields(
                                        fieldWithPath("data").description("결과 데이터"),
                                        fieldWithPath("data.id").description("아이디"),
                                        fieldWithPath("data.category").description("문의 카테고리 (제휴, 투자, 기타)"),
                                        fieldWithPath("data.phoneNumber").description("전화번호"),
                                        fieldWithPath("data.email").description("이메일"),
                                        fieldWithPath("data.title").description("제목"),
                                        fieldWithPath("data.message").description("메시지"),
                                        fieldWithPath("data.checked").description("동의 여부"),
                                        fieldWithPath("data.createDate").description("문의 날짜"),
                                        fieldWithPath("status").description("HTTP Status")
                                )
                        )
                );
    }

    @Test
    void getAllQna() throws Exception {
        Token token = tokenRepository.findByUserId(1L).get();
        mockMvc.perform(
            get("/api/qna/allqna")
                .header("TOKEN", token.getToken())
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("data").description("결과 데이터"),
                                        fieldWithPath("data[0].id").description("아이디"),
                                        fieldWithPath("data[0].category").description("문의 카테고리 (제휴, 투자, 기타)"),
                                        fieldWithPath("data[0].phoneNumber").description("전화번호"),
                                        fieldWithPath("data[0].email").description("이메일"),
                                        fieldWithPath("data[0].title").description("제목"),
                                        fieldWithPath("data[0].message").description("메시지"),
                                        fieldWithPath("data[0].checked").description("동의 여부"),
                                        fieldWithPath("data[0].createDate").description("문의 날짜"),
                                        fieldWithPath("status").description("HTTP Status")
                                )
                        )
                )
        ;
    }

    @Test
    void addQna() throws Exception {
        mockMvc.perform(
                post("/api/qna/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("" +
                                "{\n" +
                                "  \"category\": \"투자문의\",\n" +
                                "  \"phoneNumber\": \"010-2146-0894\",\n" +
                                "  \"email\": \"gmldnr2222@naver.com\",\n" +
                                "  \"title\": \"안녕하세요. 투자 관련 문의입니다.\",\n" +
                                "  \"message\": \"투자 관련 해서 미팅하고 싶습니다. 연락주세요\",\n" +
                                "  \"checked\": true\n" +
                                "}")
        )
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("category").description("카테고리"),
                                        fieldWithPath("phoneNumber").description("전화번호"),
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("title").description("제목"),
                                        fieldWithPath("message").description("메시지"),
                                        fieldWithPath("checked").description("checked")
                                ),
                                responseFields(
                                        fieldWithPath("data").description("결과 데이터"),
                                        fieldWithPath("data.result").description("QnA 추가 request 성공 여부"),
                                        fieldWithPath("data.message").description("response 메시지"),
                                        fieldWithPath("status").description("HTTP Status")
                                )
                        )
                )
        ;
    }

    @Test
    void deleteQna() throws Exception {
        Token token = tokenRepository.findByUserId(1L).get();
        mockMvc.perform(
            delete("/api/qna/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header("TOKEN", token.getToken())
        )
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("data").description("결과 데이터"),
                                        fieldWithPath("data.result").description("QnA 삭제 request 성공 여부"),
                                        fieldWithPath("data.message").description("response 메시지"),
                                        fieldWithPath("status").description("HTTP Status")
                                )
                        )
                )
        ;
    }

    @Test
    void addQna_invalid() throws Exception {
        mockMvc.perform(
                post("/api/qna/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("" +
                                "{\n" +
                                "  \"category\": \"\",\n" +
                                "  \"phoneNumber\": \"010-2146-0894\",\n" +
                                "  \"email\": \"gmldnr2222.naver.com\",\n" +
                                "  \"title\": \"안녕하세요. 투자 관련 문의입니다.\",\n" +
                                "  \"message\": \"투자 관련 해서 미팅하고 싶습니다. 연락주세요\",\n" +
                                "  \"checked\": true\n" +
                                "}")
        )
                .andExpect(status().isBadRequest())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("message").description("에러 메시지"),
                                        fieldWithPath("status").description("상태 코드"),
                                        fieldWithPath("errors").description("에러 데이터"),
                                        fieldWithPath("errors[0].field").description("에러가 발생한 필드"),
                                        fieldWithPath("errors[0].value").description("에러가 발생한 값"),
                                        fieldWithPath("errors[0].message").description("에러 메시지 상세"),
                                        fieldWithPath("code").description("에러 코드"),
                                        fieldWithPath("timeStamp").description("HTTP Status")
                                )
                        )
                )
        ;
    }

}