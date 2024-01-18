package com.green.greengram4.exception;

import lombok.Builder;
import lombok.Getter;

// resVo 같은 느낌으로 에러 발생시 리턴되는 용도
@Getter
@Builder
public class ErrorResponse {
    private final String code;
    private final String message;
}
