package com.checkyou.vacation.dto;

import com.checkyou.vacation.type.VacationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Apply {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        //        @NotBlank(message = "휴가신청 날짜를 선택하세요")
        private LocalDate startDate; // 휴가 시작
        //        @NotBlank(message = "휴가일을 선택하세요")
        private float amount; // 일단 float로 받아보기, 신청한 휴가일

        @Enumerated(EnumType.STRING)
        //        @NotBlank(message = "휴가 종류를 선택하세요")
        private VacationType vacationType;
    }

    public static class Response {}
}
