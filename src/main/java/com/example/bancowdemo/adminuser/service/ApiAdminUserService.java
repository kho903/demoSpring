package com.example.bancowdemo.adminuser.service;

import com.example.bancowdemo.adminuser.entity.AdminStatus;
import com.example.bancowdemo.adminuser.entity.ApiAdminUser;
import com.example.bancowdemo.adminuser.exception.BizException;
import com.example.bancowdemo.adminuser.exception.PasswordNotMatchException;
import com.example.bancowdemo.adminuser.exception.UserNotFoundException;
import com.example.bancowdemo.adminuser.model.UserInput;
import com.example.bancowdemo.adminuser.model.UserLoginInput;
import com.example.bancowdemo.adminuser.repository.ApiAdminUserRepository;
import com.example.bancowdemo.qna.ServiceResult;
import com.example.bancowdemo.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiAdminUserService {

    private final ApiAdminUserRepository apiAdminUserRepository;

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

        return ServiceResult.success("회원가입을 성공하였습니다.");
    }

//    public ServiceResult loginUser(UserLoginInput userLoginInput) {
//        ApiAdminUser user = apiAdminUserRepository.findByEmail(userLoginInput.getEmail())
//                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
//
//        if (!PasswordUtils.equalPassword(userLoginInput.getPassword(), user.getPassword())) {
//            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
//        }
//
//        LocalDateTime expiredDatetime = LocalDateTime.now().plusMonths(1);
//        Date expiredDate = java.sql.Timestamp.valueOf(expiredDatetime);
//
////        String token =
//    }

}
