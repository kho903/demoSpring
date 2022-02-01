package com.example.bancowdemo.adminuser.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.bancowdemo.adminuser.entity.ApiAdminUser;
import com.example.bancowdemo.adminuser.model.PasswordInput;
import com.example.bancowdemo.adminuser.model.UserFindInput;
import com.example.bancowdemo.adminuser.model.UserInput;
import com.example.bancowdemo.adminuser.model.UserLoginInput;
import com.example.bancowdemo.adminuser.model.UserLoginToken;
import com.example.bancowdemo.adminuser.service.ApiAdminUserService;
import com.example.bancowdemo.qna.ServiceResult;
import com.example.bancowdemo.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiAdminUserController {

    private final ApiAdminUserService apiAdminUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserInput userInput) {
        ServiceResult result = apiAdminUserService.registerUser(userInput);
        return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
        // json body를 맞추기 위해 다음과 같이 하였습니다. (return new Response<>(result, HttpStatus.OK); -> 왜 인지 404 오류.
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
                .sign(Algorithm.HMAC512("bancowAlgorithm".getBytes()));

        return ResponseEntity.ok().body(new Response<>(UserLoginToken.builder().token(token).build(), HttpStatus.OK));
    }

    @GetMapping("/authentication/{token}")
    public ResponseEntity<?> authentication(@PathVariable String token) {
        apiAdminUserService.authentication(token);
        return ResponseEntity.ok("인증에 성공하였습니다.");
    }

    @PostMapping("/findmanager")
    public ResponseEntity<?> findManager(@RequestBody @Valid UserFindInput userFindInput) {
        apiAdminUserService.findManager(userFindInput);
        return ResponseEntity.ok("이메일로 비밀번호 초기화 메시지가 발송되었습니다.");
    }

    @GetMapping("/authentication/findmanager/{token}")
    public ResponseEntity<?> authenticationPassword(@PathVariable String token) {
        apiAdminUserService.authenticationPassword(token);
        return ResponseEntity.ok("인증에 성공하였습니다.");
    }

    @PatchMapping("/authentication/findmanager/{token}")
    public ResponseEntity<?> changePassword(@PathVariable String token, @RequestBody @Valid PasswordInput passwordInput) {
        apiAdminUserService.changePassword(token, passwordInput);
        return ResponseEntity.ok("비밀번호 변경에 성공하였습니다.");
    }

    @PatchMapping("/status-to-admin/{token}")
    public ResponseEntity<?> statusToAdmin(@PathVariable String token, @RequestParam Long id) {
        apiAdminUserService.makeAdmin(token, id);
        return ResponseEntity.ok("해당 계정을 ADMIN 상태로 변경에 성공하였습니다.");
    }
}
