package com.checkyou.time;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeTest {

    float Total_Leave_Date = 15.0f; // 전체 연차는 15일
    LocalDate startDate = LocalDate.of(2023, 10, 10); // *휴가 시작일
    float applyDateF = 2.5f; // *휴가일수

    // 소수일수를 제거 후 날짜에 더함.
    int wholeDays = (int) applyDateF; // 정수 날짜 -> 2
    float fractionOfDay = applyDateF - wholeDays; // 소수일수 -> 0.5

    LocalDate endDate = startDate.plusDays(wholeDays); // *휴가 종료일 -> 2023.10.12

    // 남은 휴가
    float restLeave = Total_Leave_Date - applyDateF;

    @Test
    public void myTest() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info(String.valueOf(applyDateF));
    }
}
