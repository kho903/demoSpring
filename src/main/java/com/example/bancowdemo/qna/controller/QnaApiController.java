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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class QnaApiController {
    private final QnaService qnaService;

    @GetMapping("/{id}")
    public Response<?> getQna(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
        Qna qna = qnaService.getQna(token, id);
        if (qna == null) {
            return new Response<>(null, HttpStatus.BAD_REQUEST);
        }
        return new Response<>(qna, HttpStatus.OK);
    }

    @GetMapping("/allqna")
    public Response<?> getAllQna(@RequestHeader("TOKEN") String token) {
        List<Qna> allQna = qnaService.getAllQna(token);
        return new Response<>(allQna, HttpStatus.OK);
    }

    @PostMapping("/add")
    public Response<?> addQna(@RequestBody @Valid QnaInput qnaInput) {
        ServiceResult result = qnaService.addQna(qnaInput);
        return new Response<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Response<?> deleteQna(@PathVariable Long id, @RequestHeader("TOKEN") String token) {
        ServiceResult result = qnaService.deleteQna(token, id);

        if (!result.isResult()) {
            return new Response<>(result, HttpStatus.BAD_REQUEST);
        }

        return new Response<>(result, HttpStatus.OK);
    }

}
