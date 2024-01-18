package com.green.greengram4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
// 런타임 익셉션
// 컴파일 익셉션
@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;
}
