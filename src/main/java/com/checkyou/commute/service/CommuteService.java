package com.checkyou.commute.service;

import com.checkyou.auth.entity.Member;
import com.checkyou.commute.dto.CheckIn.Request;
import com.checkyou.commute.entity.Commute;
import com.checkyou.commute.repository.CommuteRepository;
import com.checkyou.global.exception.CustomException;
import com.checkyou.global.exception.ErrorCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommuteService {

    private static final LocalDateTime TARDINESS =
            LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 1));
    private final CommuteRepository commuteRepository;

    public Commute checkIn(Member member, Request request) {

        // 1. 출근시간이 지각인지 확인
        boolean tardiness = TARDINESS.isBefore(request.getStartTime());

        // 만약에 이미 오늘 출근했으면 오류 (이미 출근 함)

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.with(LocalTime.MIN);
        LocalDateTime endOfDay = now.with(LocalTime.MAX);

        if (commuteRepository.existsByMemberAndCreatedAtBetween(member, startOfDay, endOfDay)) {
            throw new CustomException(ErrorCode.ALREADY_CHECKED_IN);
        }

        // 2. 출근시간을 저장한다.
        return commuteRepository.save(
                Commute.builder()
                        .member(member)
                        .startTime(request.getStartTime())
                        .tardiness(tardiness)
                        .build());
    }
}
