package com.example.bancowdemo.adminuser.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.bancowdemo.adminuser.entity.AdminStatus;
import com.example.bancowdemo.adminuser.entity.ApiAdminUser;
import com.example.bancowdemo.adminuser.exception.BizException;
import com.example.bancowdemo.adminuser.exception.PasswordNotMatchException;
import com.example.bancowdemo.adminuser.exception.UserNotFoundException;
import com.example.bancowdemo.adminuser.model.PasswordInput;
import com.example.bancowdemo.adminuser.model.UserFindInput;
import com.example.bancowdemo.adminuser.model.UserInput;
import com.example.bancowdemo.adminuser.model.UserLoginInput;
import com.example.bancowdemo.adminuser.repository.ApiAdminUserRepository;
import com.example.bancowdemo.mail.MailComponent;
import com.example.bancowdemo.mail.entity.MailTemplate;
import com.example.bancowdemo.mail.repository.MailTemplateRepository;
import com.example.bancowdemo.qna.ServiceResult;
import com.example.bancowdemo.token.entity.Token;
import com.example.bancowdemo.token.repository.TokenRepository;
import com.example.bancowdemo.util.PasswordUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiAdminUserService {

    private final ApiAdminUserRepository apiAdminUserRepository;
    private final MailComponent mailComponent;
    private final MailTemplateRepository mailTemplateRepository;
    private final TokenRepository tokenRepository;

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
        sendMail(user, "ADMIN_REGISTER");

        return ServiceResult.success("회원가입을 성공하였습니다.");
    }

    public ServiceResult loginUser(UserLoginInput userLoginInput) {
        ApiAdminUser user = apiAdminUserRepository.findByEmail(userLoginInput.getEmail())
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        if (!PasswordUtils.equalPassword(userLoginInput.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        String userAuthenticationKey = makeJwtToken(user);
        Token token = Token.builder()
            .token(userAuthenticationKey)
            .user(user)
            .expiredDate(LocalDateTime.now().plusDays(1))
            .build();
        tokenRepository.save(token);
        // return user;
        return ServiceResult.success(user.getUsername() + " 님의 로그인에 성공하였습니다.");
    }

    public ServiceResult authentication(String token) {
        Token findToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new BizException("Not Found Token"));
        String email = findToken.getUser().getEmail();
        ApiAdminUser user = apiAdminUserRepository.findByEmail(email)
            .orElseThrow(() -> new BizException("User Not Found"));
        user.setAdminStatus(AdminStatus.PENDING_SUPER);
        apiAdminUserRepository.save(user);
        tokenRepository.delete(findToken);

        return ServiceResult.success(user.getUsername() + " 님의 인증에 성공하였습니다.");
    }

    public ServiceResult findManager(UserFindInput userFindInput) {
        Optional<ApiAdminUser> optionalUser = apiAdminUserRepository.findByEmail(userFindInput.getEmail());
        if (optionalUser.isEmpty()) {
            throw new BizException("존재하지 않는 이메일입니다.");
        }
        ApiAdminUser user = optionalUser.get();
        if (!user.getUsername().equals(userFindInput.getUsername())) {
            throw new BizException("회원정보가 틀립니다.");
        }

        sendMail(user, "FIND_MANAGER");
        return ServiceResult.success(user.getUsername() + " 님의 이메일로 비밀번호 초기화 메시지가 발송되었습니다.");
    }

    private void sendMail(ApiAdminUser user, String templateId) {
        String serverURL = "http://localhost:8080";

        String userAuthenticationKey = makeJwtToken(user);
        Token token = Token.builder()
            .token(userAuthenticationKey)
            .user(user)
            .expiredDate(LocalDateTime.now().plusDays(1))
            .build();

        tokenRepository.save(token);

        Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId(templateId);
        optionalMailTemplate.ifPresent(e -> {
            String fromEmail = e.getSendEmail();
            String fromUserName = e.getSendUserName();
            String title = e.getTitle().replaceAll("\\{USER_NAME\\}", user.getUsername());
            String contents = e.getContents().replaceAll("\\{USER_NAME\\}", user.getUsername())
                    .replaceAll("\\{SERVER_URL\\}", serverURL)
                    .replaceAll("\\{USER_AUTHENTICATION_KEY\\}", userAuthenticationKey);

            mailComponent.send(fromEmail, fromUserName, user.getEmail(), user.getUsername(), title, contents);
        });
    }

    public ApiAdminUser authenticationPassword(String token) {
        Token findToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new BizException("Not Found Token"));
        String email = findToken.getUser().getEmail();
        tokenRepository.delete(findToken);
        return apiAdminUserRepository.findByEmail(email).orElseThrow(() -> new BizException("User Not Found"));
    }

    public ServiceResult changePassword(String token, PasswordInput passwordInput) {
        ApiAdminUser user = authenticationPassword(token);
        if (!passwordInput.getPassword1().equals(passwordInput.getPassword2())) {
            throw new BizException("비밀번호1, 2가 일치하지 않습니다.");
        }
        String encryptPassword = PasswordUtils.encryptedPassword(passwordInput.getPassword1());
        user.setPassword(encryptPassword);
        apiAdminUserRepository.save(user);

        return ServiceResult.success(user.getUsername() + " 님의 비밀번호 변경에 성공하였습니다.");
    }

    public ServiceResult makeAdmin(String token, Long id) {
        Token findToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new BizException("토큰 정보를 찾을 수 없습니다."));
        AdminStatus userStatus = findToken.getUser().getAdminStatus();
        if (!userStatus.equals(AdminStatus.SUPER)) {
            throw new BizException("슈퍼 계정이 아닙니다.");
        }

        ApiAdminUser user = apiAdminUserRepository.findById(id)
            .orElseThrow(() -> new BizException("해당 정보의 유저가 존재하지 않습니다."));
        user.setAdminStatus(AdminStatus.ADMIN);
        apiAdminUserRepository.save(user);

        return ServiceResult.success(user.getUsername() + " 님의 상태를 ADMIN으로 변경하였습니다.");
    }

    public String makeJwtToken(ApiAdminUser user) {
        LocalDateTime expiredDatetime = LocalDateTime.now().plusMonths(1);
        Date expiredDate = java.sql.Timestamp.valueOf(expiredDatetime);
        return JWT.create()
            .withExpiresAt(expiredDate)
            .withClaim("user_id", user.getId())
            .withSubject(user.getUsername())
            .withIssuer(user.getEmail())
            .sign(Algorithm.HMAC512("bancowAlgorithm".getBytes()));
    }
}