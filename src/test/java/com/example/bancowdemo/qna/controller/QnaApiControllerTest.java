package com.example.bancowdemo.qna.controller;

import com.example.bancowdemo.TestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QnaApiControllerTest extends TestSupport {

    @Test
    void getQna() throws Exception {
        mockMvc.perform(
                get("/api/qna/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
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
        mockMvc.perform(
                get("/api/qna/allqna")
                        .contentType(MediaType.APPLICATION_JSON)
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
        mockMvc.perform(
                delete("/api/qna/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
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
}