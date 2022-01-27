package com.example.bancowdemo.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class Response<T> {
    private T data;
    private HttpStatus status;
}
