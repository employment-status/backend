package com.checkyou.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_EXIST_MEMBER("존재하지 않는 회원 입니다.", HttpStatus.NOT_FOUND),
    ALREADY_CHECKED_IN("이미 출근 했습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
