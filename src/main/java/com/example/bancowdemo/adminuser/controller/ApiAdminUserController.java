package com.example.bancowdemo.adminuser.controller;

import javax.validation.Valid;

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

import com.example.bancowdemo.adminuser.model.PasswordInput;
import com.example.bancowdemo.adminuser.model.UserFindInput;
import com.example.bancowdemo.adminuser.model.UserInput;
import com.example.bancowdemo.adminuser.model.UserLoginInput;
import com.example.bancowdemo.adminuser.service.ApiAdminUserService;
import com.example.bancowdemo.qna.ServiceResult;
import com.example.bancowdemo.response.Response;

import lombok.RequiredArgsConstructor;

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
        ServiceResult result = apiAdminUserService.loginUser(userLoginInput);
        return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
    }

    @GetMapping("/authentication/{token}")
    public ResponseEntity<?> authentication(@PathVariable String token) {
        ServiceResult result = apiAdminUserService.authentication(token);
        return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
    }

    @PostMapping("/findmanager")
    public ResponseEntity<?> findManager(@RequestBody @Valid UserFindInput userFindInput) {
        ServiceResult result = apiAdminUserService.findManager(userFindInput);
        return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
    }

    @GetMapping("/authentication/findmanager/{token}")
    public ResponseEntity<?> authenticationPassword(@PathVariable String token) {
        apiAdminUserService.authenticationPassword(token);
        return ResponseEntity.ok().body(new Response<>("비밀번호 변경을 위한 인증에 성공하였습니다.", HttpStatus.OK));
    }

    @PatchMapping("/authentication/findmanager/{token}")
    public ResponseEntity<?> changePassword(@PathVariable String token, @RequestBody @Valid PasswordInput passwordInput) {
        ServiceResult result = apiAdminUserService.changePassword(token, passwordInput);
        return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
    }

    @PatchMapping("/status-to-admin/{token}")
    public ResponseEntity<?> statusToAdmin(@PathVariable String token, @RequestParam Long id) {
        ServiceResult result = apiAdminUserService.makeAdmin(token, id);
        return ResponseEntity.ok().body(new Response<>(result, HttpStatus.OK));
    }
}
