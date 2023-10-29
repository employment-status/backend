package com.checkyou.vacation.dto;

import com.checkyou.vacation.entity.ConfirmVacation;
import com.checkyou.vacation.type.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.*;

public class Confirmation {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate endDate;

        private Status status;

        public static Response from(ConfirmVacation confirmVacation) {

            return Response.builder()
                    .status(confirmVacation.getStatus())
                    .startDate(confirmVacation.getStartDate())
                    .endDate(confirmVacation.getEndDate())
                    .build();
        }
    }
}
