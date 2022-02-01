package com.example.bancowdemo.adminuser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordInput {

    @NotBlank(message = "비밀번호1은 필수 항목입니다.")
    private String password1;

    @NotBlank(message = "비밀번호2는 필수 항목입니다.")
    private String password2;
}
