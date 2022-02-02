package com.example.bancowdemo.qna.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.bancowdemo.adminuser.entity.AdminStatus;
import com.example.bancowdemo.adminuser.exception.BizException;
import com.example.bancowdemo.qna.ServiceResult;
import com.example.bancowdemo.qna.entity.Qna;
import com.example.bancowdemo.qna.entity.QnaInput;
import com.example.bancowdemo.qna.repository.QnaRepository;
import com.example.bancowdemo.util.token.entity.Token;
import com.example.bancowdemo.util.token.repository.TokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final TokenRepository tokenRepository;

    public Qna getQna(String token, Long qnaId) {
        checkTokenValid(token);
        Optional<Qna> optionalQna = qnaRepository.findById(qnaId);
        if (!optionalQna.isPresent()) {
            return null;
        }
        Qna qna = optionalQna.get();
        return qnaRepository.save(qna);
    }

    public List<Qna> getAllQna(String token) {
        checkTokenValid(token);
        return qnaRepository.findAll();
    }

    public ServiceResult addQna(QnaInput qnaInput) {

        Qna qna = Qna.builder()
                .category(qnaInput.getCategory())
                .phoneNumber(qnaInput.getPhoneNumber())
                .email(qnaInput.getEmail())
                .title(qnaInput.getTitle())
                .message(qnaInput.getMessage())
                .checked(qnaInput.isChecked())
                .createDate(LocalDateTime.now())
                .build();

        qnaRepository.save(qna);
        return ServiceResult.success("QNA를 성공적으로 등록하였습니다.");
    }

    public ServiceResult deleteQna(String token, Long qnaId) {
        checkTokenValid(token);
        Optional<Qna> optionalQna = qnaRepository.findById(qnaId);
        if (!optionalQna.isPresent()) {
            return ServiceResult.fail("삭제할 QNA가 없습니다.");
        }
        Qna qna = optionalQna.get();

        qnaRepository.delete(qna);
        return ServiceResult.success("QNA를 성공적으로 삭제하였습니다.");
    }

    private void checkTokenValid(String token) {
        Token findToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new BizException("Not Found Token"));
        if (!(findToken.getUser().getAdminStatus().equals(AdminStatus.ADMIN) ||
            findToken.getUser().getAdminStatus().equals(AdminStatus.SUPER))) {
            throw new BizException("유저 권한이 없습니다.");
        }
    }
}
