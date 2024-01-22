package com.green.greengram4.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Filter, 언터셉터, AOP : 미들웨어처럼 중간에 끼울 수 있는 슬롯느낌
@Slf4j
@RestControllerAdvice //AOP - 노션 // 어떤 언어로 개발했는지 안뜨게 할 수 있습니다.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /*@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity <Object> handleIllegalArgument(IllegalArgumentException e){
        log.warn("handleIllegalArgument", e);
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER);
    }

*//*    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    }*//*

    @ExceptionHandler(MethodArgumentNotValidException.class) // 에러담당자가 왼쪽과 같이 있다면
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){ // 에러들을 왼쪽에 담습니다.
        log.warn("handleMethodArgumentNotValidException", e);
*//*        List<String> errors = new ArrayList<>();
        for ( FieldError lfe : e.getBindingResult().getFieldErrors() ) {
            errors.add(lfe.getDefaultMessage());
            // 객체마다 디폴트 메세지 안에 하나씩 갖고있는데
            // 거기다가 하나씩 add합니다.
        } // 스트림 이용 x*//*
        List<String> errors = e.getBindingResult()
                                .getFieldErrors()
                                .stream() // 1. 넘어온 리스트를 스트림으로 바꿉니다.
                                .map(lfe -> lfe.getDefaultMessage())
                                 // map : 같은 사이즈의 애를 리턴합니다. filter : 커지지않고 같거나 같아집니다.
                // getDefaultMessage() 가 return타입이 Str이니까 Str타입배열을 만듭니다.
                                .collect(Collectors.toList());Collectors.toList(); // Collect로 리스트타입으로 변환


        String errStr = "[" + String.join(", ", errors) + "]"; // 마지막에 , 가 안붙음
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER, errors.toString());
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
    }*/

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.warn("handleException", e);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVAL_ERROR);
    }
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e) {
        log.warn("handleRestApiException", e);
        return handleExceptionInternal(e.getErrorCode());
    }
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return handleExceptionInternal(errorCode, null);
    }
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode
            , String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(message == null
                        ? makeErrorResponse(errorCode)
                        : makeErrorResponse(errorCode, message));
    }
    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return makeErrorResponse(errorCode, errorCode.getMessage());
    }
    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }
}
