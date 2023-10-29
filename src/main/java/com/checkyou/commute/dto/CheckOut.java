package com.checkyou.commute.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CheckOut {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm",
                timezone = "Asia/Seoul")
        private LocalDateTime endTime;
    }
}
