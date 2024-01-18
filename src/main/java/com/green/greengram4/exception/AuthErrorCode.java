package com.green.greengram4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

// 로그인 관련에러입니다.
@Getter // 얘가 getHttpStatus(), getMessage()를 구현해주어 에러가 안뜹
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode{
    NOT_EXIST_USER_TO(HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다."),
    VALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해 주세요"),
    NEED_SIGNIN(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND
            , "refresh-token이 없습니다.");
    // 상수 객체화를 하여 담겠다는 의미입니다.
    private final HttpStatus httpStatus;
    private final String message;
}
