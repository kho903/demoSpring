package com.example.bancowdemo.qna.service;

import com.example.bancowdemo.qna.ServiceResult;
import com.example.bancowdemo.qna.entity.Qna;
import com.example.bancowdemo.qna.entity.QnaInput;
import com.example.bancowdemo.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    public Qna getQna(Long qnaId) {
        return qnaRepository.findById(qnaId).get();
    }

    public List<Qna> getAllQna() {
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

    public ServiceResult deleteQna(Long qnaId) {
        Optional<Qna> optionalQna = qnaRepository.findById(qnaId);
        if (!optionalQna.isPresent()) {
            return ServiceResult.fail("삭제할 QNA가 없습니다.");
        }
        Qna qna = optionalQna.get();

        qnaRepository.delete(qna);
        return ServiceResult.success("QNA를 성공적으로 삭제하였습니다.");
    }
}
