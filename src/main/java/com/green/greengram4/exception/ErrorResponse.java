package com.green.greengram4.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

// resVo 같은 느낌으로 에러 발생시 리턴되는 용도
@Getter
@Builder
public class ErrorResponse {
    private final String code;
    private final String message;
    // Errors가 없다면 응답이 내려가지 않게 처리
    // valid가 null이 아니고 size 1이상 이라면 JSON으로 만들어 진다. 아니면 안만들어진다.
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> valid;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {
        // @Valid 로 에러가 들어왔을 때, 어느 필드에서 에러가 발생했는 지에 대한 응답 처리
        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            // 맴버변수는 바뀌면 안되기에 final을 붙입니다.
            return ValidationError.builder()
                    .field(fieldError.getField()) // 맴버필드이름
                    .message(fieldError.getDefaultMessage()) // 메세지명
                    .build();
        }
    }
}
