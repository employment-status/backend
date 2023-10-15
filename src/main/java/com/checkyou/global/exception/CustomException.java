package com.checkyou.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode; // ALREADY_ATTENDENCE
    private final int status; // 404
    private final String message; // 이미 출근했습니다.

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus().value();
    }

}
