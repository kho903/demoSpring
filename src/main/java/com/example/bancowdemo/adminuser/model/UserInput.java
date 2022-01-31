package com.example.bancowdemo.adminuser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {

    @Email(message = "이메일 형식에 맞게 입력해 주세요.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @Size(min = 4, message = "비밀번호는 4자 이상 입력해 주십시오.")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @Size(min = 4, message = "비밀번호는 4자 이상 입력해 주십시오.")
    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String password2;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String username;

}
