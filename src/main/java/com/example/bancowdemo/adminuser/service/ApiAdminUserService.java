package com.example.bancowdemo.adminuser.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.bancowdemo.adminuser.entity.AdminStatus;
import com.example.bancowdemo.adminuser.entity.ApiAdminUser;
import com.example.bancowdemo.adminuser.exception.BizException;
import com.example.bancowdemo.adminuser.exception.PasswordNotMatchException;
import com.example.bancowdemo.adminuser.exception.UserNotFoundException;
import com.example.bancowdemo.adminuser.model.UserInput;
import com.example.bancowdemo.adminuser.model.UserLoginInput;
import com.example.bancowdemo.adminuser.repository.ApiAdminUserRepository;
import com.example.bancowdemo.mail.MailComponent;
import com.example.bancowdemo.mail.entity.MailTemplate;
import com.example.bancowdemo.mail.repository.MailTemplateRepository;
import com.example.bancowdemo.qna.ServiceResult;
import com.example.bancowdemo.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiAdminUserService {

    private final ApiAdminUserRepository apiAdminUserRepository;
    private final MailComponent mailComponent;
    private final MailTemplateRepository mailTemplateRepository;

    public ServiceResult registerUser(UserInput userInput) {
        Optional<ApiAdminUser> optionalUser = apiAdminUserRepository.findByEmail(userInput.getEmail());
        if (optionalUser.isPresent()) {
            throw new BizException("이미 가입된 이메일입니다.");
        }

        if (!(userInput.getPassword().equals(userInput.getPassword2()))) {
            throw new BizException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        String encryptPassword = PasswordUtils.encryptedPassword(userInput.getPassword());

        ApiAdminUser user = ApiAdminUser.builder()
                .email(userInput.getEmail())
                .password(encryptPassword)
                .username(userInput.getUsername())
                .adminStatus(AdminStatus.PENDING_EMAIL)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        apiAdminUserRepository.save(user);

        // 메일 전송.
        String serverURL = "http://localhost:8080";

        String userAuthenticationKey = UUID.randomUUID().toString();

        Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId("ADMIN_REGISTER");
        optionalMailTemplate.ifPresent(e -> {
            String fromEmail = e.getSendEmail();
            String fromUserName = e.getSendUserName();
            String title = e.getTitle().replaceAll("\\{USER_NAME\\}", user.getUsername());
            String contents = e.getContents().replaceAll("\\{USER_NAME\\}", user.getUsername())
                    .replaceAll("\\{SERVER_URL\\}", serverURL)
                    .replaceAll("\\{USER_AUTHENTICATION_KEY\\}", userAuthenticationKey);

            mailComponent.send(fromEmail, fromUserName, user.getEmail(), user.getUsername(), title, contents);
        });


        return ServiceResult.success("회원가입을 성공하였습니다.");
    }

    public ApiAdminUser loginUser(UserLoginInput userLoginInput) {
        ApiAdminUser user = apiAdminUserRepository.findByEmail(userLoginInput.getEmail())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        if (!PasswordUtils.equalPassword(userLoginInput.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

}
