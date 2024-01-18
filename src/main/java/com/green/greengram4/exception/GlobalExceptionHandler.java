package com.green.greengram4.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Filter, 언터셉터, AOP : 미들웨어처럼 중간에 끼울 수 있는 슬롯느낌
@Slf4j
@RestControllerAdvice //AOP - 노션 // 어떤 언어로 개발했는지 안뜨게 할 수 있습니다.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity <Object> handleIllegalArgument(IllegalArgumentException e){
        log.warn("handleIllegalArgument", e);
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER);
    }

    // 대부분의 예외를 이친구가 잡습니다. -> 인터넷 서버에러가 뜨도록
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e){
        log.warn("handelException", e);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVAL_ERROR);
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e){
        log.warn("handleIllegalArgument", e);
        return handleExceptionInternal(e.getErrorCode());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode){
        return handleExceptionInternal(errorCode, null);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode
            , String message){
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(message == null ? makeErrorResponse(errorCode)
                                        : makeErrorResponse(errorCode, message));
    }
    private ErrorResponse makeErrorResponse(ErrorCode errorCode){
        return makeErrorResponse(errorCode, errorCode.getMessage());
        // 기본 메세지 사용 시 얘를 호출하며
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message){
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
        // 다른 메세지를 사용 시 얘를 호출합니다.
    }
}
