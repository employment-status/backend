package com.checkyou.commute.service;

import com.checkyou.auth.entity.Member;
import com.checkyou.auth.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final CommuteRepository commuteRepository;

    public Commute checkIn(Request request) {
        // 사번이랑 출근시간
        boolean tardiness = false;

        // 1. 사번으로 멤버를 불러온다. 해당 멤버가 없으면 오류
        Member member =
                memberRepository
                        .findByCode(request.getCode())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        // 3. 출근시간이 지각인지 확인

        if (TARDINESS.isBefore(request.getStartTime())) {
            tardiness = true;
        }
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
