package com.checkyou.vacation.service;

import com.checkyou.auth.entity.Member;
import com.checkyou.global.exception.CustomException;
import com.checkyou.global.exception.ErrorCode;
import com.checkyou.vacation.dto.Apply;
import com.checkyou.vacation.dto.Confirmation;
import com.checkyou.vacation.dto.RestVacation;
import com.checkyou.vacation.entity.ConfirmVacation;
import com.checkyou.vacation.entity.Vacation;
import com.checkyou.vacation.repository.ConfirmVacationRepository;
import com.checkyou.vacation.repository.VacationRepository;
import com.checkyou.vacation.type.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.*;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VacationService {
    @PersistenceContext private EntityManager entityManager;

    private final ConfirmVacationRepository confirmVacationRepository;
    private final VacationRepository vacationRepository;

    // * api/ 휴가 신청

    @Transactional
    public void apply(Member member, Apply.Request request) {
        List<ConfirmVacation> confirmVacations = confirmVacationRepository.findByMember(member);

        if (LocalDate.now().isAfter(request.getStartDate())) {
            throw new CustomException(ErrorCode.IMPOSSIBLE_TO_APPLY_DATE);
        }

        LocalDate endDate = request.getStartDate().plusDays((int) Math.floor(request.getAmount()));

        for (ConfirmVacation confirmVacation : confirmVacations) {

            if ((isVacationOverlaps(confirmVacation, request.getStartDate(), endDate))) {
                throw new CustomException(ErrorCode.IMPOSSIBLE_TO_APPLY_DATE); // 겹치면 예외처리
            }
        }

        // 4. DB에 저장
        ConfirmVacation confirmVacation =
                confirmVacationRepository.save(
                        ConfirmVacation.builder()
                                .member(member)
                                .startDate(request.getStartDate())
                                .amount(request.getAmount()) // 추후 삭제??
                                .vacationType(request.getVacationType())
                                .status(Status.WAIT)
                                .endDate(endDate)
                                .build());
    }

    public boolean isVacationOverlaps(
            ConfirmVacation confirmVacation, LocalDate applyStartDate, LocalDate applyEndDate) {
        LocalDate approvalStartDate = confirmVacation.getStartDate();
        LocalDate approvalEndDate = confirmVacation.getEndDate(); // 기신청한 휴가종료일
        // 새로 신청한 휴가시작일과 휴가종료일이 기존 휴가와 겹치는지 확인
        return !((approvalEndDate.isBefore(applyStartDate))
                || (approvalStartDate.isAfter(applyEndDate)));
    }

    // api - 휴가 일수 확인
    @Transactional
    public RestVacation restVacation(Member member) {
        Vacation vacation = vacationRepository.findByMember(member);
        return RestVacation.builder()
                .annualLeave(vacation.getAnnualLeave())
                .additionalLeave(vacation.getAdditionalLeave())
                .sickLeave(vacation.getSickLeave())
                .build();
    }

    @Transactional
    public List<Confirmation.Response> confirmation(Member member, int year, Integer month) {

        LocalDate firstDay = LocalDate.of(year, 1, 1);
        LocalDate lastDay = YearMonth.of(year, 12).atEndOfMonth();

        if (month != null) {
            firstDay = LocalDate.of(year, month, 1);
            lastDay = YearMonth.of(year, month).atEndOfMonth();
        }

        return confirmVacationRepository
                .findByMemberAndStartDateBetweenOrderByStartDateDesc(member, firstDay, lastDay)
                .stream()
                .map(Confirmation.Response::from)
                .collect(Collectors.toList());
    }
}
