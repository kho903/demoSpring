package com.example.bancowdemo.qna.controller;

import com.example.bancowdemo.qna.ServiceResult;
import com.example.bancowdemo.qna.entity.Qna;
import com.example.bancowdemo.qna.entity.QnaInput;
import com.example.bancowdemo.qna.service.QnaService;
import com.example.bancowdemo.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class QnaApiController {
    private final QnaService qnaService;

    @GetMapping("/{id}")
    public Response<?> getQna(@PathVariable Long id) {
        Qna qna = qnaService.getQna(id);
        return new Response<>(qna, HttpStatus.OK);
    }

    @GetMapping("/allqna")
    public Response<?> getAllQna() {
        List<Qna> allQna = qnaService.getAllQna();
        return new Response<>(allQna, HttpStatus.OK);
    }

    @PostMapping("/add")
    public Response<?> addQna(@RequestBody QnaInput qnaInput) {
        ServiceResult result = qnaService.addQna(qnaInput);
        return new Response<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Response<?> deleteQna(@PathVariable Long id) {
        ServiceResult result = qnaService.deleteQna(id);

        if (!result.isResult()) {
            return new Response<>(result, HttpStatus.BAD_REQUEST);
        }

        return new Response<>(result, HttpStatus.OK);
    }

}