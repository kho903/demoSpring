package com.example.bancowdemo.adminuser.controller;

import com.example.bancowdemo.adminuser.model.UserInput;
import com.example.bancowdemo.adminuser.model.UserLoginInput;
import com.example.bancowdemo.adminuser.service.ApiAdminUserService;
import com.example.bancowdemo.qna.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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

        return null;
    }
}
