package com.checkyou.commute.dto;

import com.checkyou.commute.entity.Commute;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommuteDto {

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm",
            timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm",
            timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    private LocalTime overTime;
    private boolean tardiness;

    public static CommuteDto from(Commute commute) {
        return CommuteDto.builder()
                .startTime(commute.getStartTime())
                .endTime(commute.getEndTime())
                .overTime(commute.getOverTime())
                .tardiness(commute.isTardiness())
                .build();
    }
}
