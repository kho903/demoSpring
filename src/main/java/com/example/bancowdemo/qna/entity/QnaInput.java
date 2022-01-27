package com.example.bancowdemo.qna.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnaInput {

    @NotEmpty
    private String category;

    @NotEmpty
    private String phoneNumber;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String title;

    @NotEmpty
    private String message;

    @NotNull
    private boolean checked;
}
