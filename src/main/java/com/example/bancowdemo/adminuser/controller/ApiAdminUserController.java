package com.example.bancowdemo.adminuser.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.bancowdemo.adminuser.entity.ApiAdminUser;
import com.example.bancowdemo.adminuser.model.UserInput;
import com.example.bancowdemo.adminuser.model.UserLoginInput;
import com.example.bancowdemo.adminuser.model.UserLoginToken;
import com.example.bancowdemo.adminuser.service.ApiAdminUserService;
import com.example.bancowdemo.qna.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiAdminUserController {

    private final ApiAdminUserService apiAdminUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserInput userInput) {
        ServiceResult result = apiAdminUserService.registerUser(userInput);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginInput userLoginInput) {

        ApiAdminUser user = apiAdminUserService.loginUser(userLoginInput);
        LocalDateTime expiredDatetime = LocalDateTime.now().plusMonths(1);
        Date expiredDate = java.sql.Timestamp.valueOf(expiredDatetime);

        String token = JWT.create()
                .withExpiresAt(expiredDate)
                .withClaim("user_id", user.getId())
                .withSubject(user.getUsername())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512("bancowAlhorithm".getBytes()));

        return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());
    }
}
