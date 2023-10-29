package com.checkyou.vacation.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestVacation {

    private float annualLeave;
    private float sickLeave;
    private float additionalLeave;
}
