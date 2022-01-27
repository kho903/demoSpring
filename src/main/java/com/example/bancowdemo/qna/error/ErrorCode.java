package com.example.bancowdemo.qna.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT_ERROR("C001", "Invalid Input Error", 400),
    INTERNAL_SERVER_ERROR("C004", "Internal Server Error", 500);

    private final String code;
    private final String message;
    private final int status;
}
