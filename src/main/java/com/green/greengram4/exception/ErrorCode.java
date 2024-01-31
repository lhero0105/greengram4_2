package com.green.greengram4.exception;


import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name(); // enum에 있는 메소드라 구현 x
    HttpStatus getHttpStatus();
    String getMessage();
}
