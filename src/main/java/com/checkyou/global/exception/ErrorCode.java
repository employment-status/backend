package com.checkyou.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_EXIST_MEMBER("존재하지 않는 회원 입니다.", HttpStatus.NOT_FOUND),
    VALIDATION_FAILED("validation 오류", HttpStatus.BAD_REQUEST),
    ENUM_VALIDATION("enum class validation 오류", HttpStatus.BAD_REQUEST),
    NOT_FOUND_START_TIME("오늘 출근한 기록이 없습니다.", HttpStatus.BAD_REQUEST),
    END_TIME_MUST_BE_IN_TODAY("퇴글 날짜가 오늘 날짜와 다릅니다.", HttpStatus.BAD_REQUEST),
    FAILED_LOGIN("사번 혹은 비밀번호를 확인해주세요.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("알 수 없는 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ALREADY_SIGNUP("이미 가입된 사번입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_CHECKED_IN("이미 출근 했습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_CHECKED_OUT("이미 퇴근 했습니다.", HttpStatus.BAD_REQUEST),
    NOT_CORRECT_END_TIME("퇴근시간은 출근시간보다 이전일 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_CORRECT_START_TIME("퇴근시간 이후에는 출근할 수 없습니다.", HttpStatus.BAD_REQUEST),
    IMPOSSIBLE_TO_APPLY_DATE("휴가 신청 날짜를 확인해주세요", HttpStatus.BAD_REQUEST),

    NOT_ENOUGH_VACATION_DAYS("휴가 일수가 충분하지 않습니다", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
