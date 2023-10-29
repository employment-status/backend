package com.checkyou.commute.service;

import com.checkyou.auth.entity.Member;
import com.checkyou.commute.dto.CheckIn.Request;
import com.checkyou.commute.dto.CheckOut;
import com.checkyou.commute.entity.Commute;
import com.checkyou.commute.repository.CommuteRepository;
import com.checkyou.global.exception.CustomException;
import com.checkyou.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommuteService {

    private static final LocalDateTime TARDINESS =
            LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 1));
    private static final LocalDateTime STANDARD_END_TIME =
            LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0));

    private final CommuteRepository commuteRepository;

    @Transactional
    public void checkIn(Member member, Request request) {

        LocalDateTime startTime = request.getStartTime();

        validateStartTime(startTime);

        boolean tardiness = TARDINESS.isBefore(startTime);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.with(LocalTime.MIN);
        LocalDateTime endOfDay = now.with(LocalTime.MAX);

        if (commuteRepository.existsByMemberAndCreatedAtBetween(member, startOfDay, endOfDay)) {
            throw new CustomException(ErrorCode.ALREADY_CHECKED_IN);
        }

        commuteRepository.save(
                Commute.builder().member(member).startTime(startTime).tardiness(tardiness).build());
    }

    @Transactional
    public void checkOut(Member member, CheckOut.Request request) {

        LocalDateTime endTime = request.getEndTime();

        validationIsToday(endTime);

        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        Commute commute =
                commuteRepository
                        .findByMemberAndStartTimeBetween(member, startOfDay, endOfDay)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_START_TIME));

        validateEndTime(commute, endTime);

        calculateOverTime(endTime, commute);

        commute.setEndTime(endTime);
    }

    @Transactional
    public List<Commute> getCommuteList(Member member, int year, Integer month) {

        LocalDateTime startOfMonth = LocalDateTime.of(LocalDate.of(year, 1, 1), LocalTime.MIN);
        LocalDateTime endOfMonth =
                LocalDateTime.of(YearMonth.of(year, 12).atEndOfMonth(), LocalTime.MAX);

        if (month != null) {
            startOfMonth = LocalDateTime.of(LocalDate.of(year, month, 1), LocalTime.MIN);
            endOfMonth = startOfMonth.plusMonths(1).minusNanos(1);
        }

        // 해당 멤버의 출근 기록을 조회
        return commuteRepository.findALLByMemberAndStartTimeBetweenOrderByStartTimeAsc(
                member, startOfMonth, endOfMonth);
    }

    private void validateStartTime(LocalDateTime startTime) {
        validationIsToday(startTime);
        validationIsAfterStandardTime(startTime);
    }

    private void validateEndTime(Commute commute, LocalDateTime endTime) {
        validationAlreadyExistEndTime(commute);
        validationIsCorrectEndTime(commute, endTime);
    }

    private void validationAlreadyExistEndTime(Commute commute) {
        if (commute.getEndTime() != null) {
            throw new CustomException(ErrorCode.ALREADY_CHECKED_OUT);
        }
    }

    private void validationIsCorrectEndTime(Commute commute, LocalDateTime endTime) {
        if (endTime.isBefore(commute.getStartTime())) {
            throw new CustomException(ErrorCode.NOT_CORRECT_END_TIME);
        }
    }

    private void calculateOverTime(LocalDateTime endTime, Commute commute) {
        if (endTime.isAfter(STANDARD_END_TIME)) {
            Duration overtimeDuration = Duration.between(STANDARD_END_TIME, endTime);
            int hours = (int) overtimeDuration.toHours();
            int minutes = (int) (overtimeDuration.toMinutes() % 60);
            LocalTime overTime = LocalTime.of(hours, minutes);

            commute.setOverTime(overTime);
        }
    }

    private void validationIsAfterStandardTime(LocalDateTime startTime) {
        if (startTime.isAfter(STANDARD_END_TIME)) {
            throw new CustomException(ErrorCode.NOT_CORRECT_START_TIME);
        }
    }

    private void validationIsToday(LocalDateTime time) {
        if (!Objects.equals(time.toLocalDate(), LocalDate.now())) {
            throw new CustomException(ErrorCode.END_TIME_MUST_BE_IN_TODAY);
        }
    }
}
