package com.checkyou.vacation.service;

import com.checkyou.auth.entity.Member;
import com.checkyou.global.exception.CustomException;
import com.checkyou.global.exception.ErrorCode;
import com.checkyou.vacation.dto.Apply;
import com.checkyou.vacation.entity.ConfirmVacation;
import com.checkyou.vacation.repository.ConfirmVacationRepository;
import com.checkyou.vacation.type.Status;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VacationService {

    private final ConfirmVacationRepository confirmVacationRepository;

    // * api/ 휴가 신청

    @Transactional
    public void apply(Member member, Apply.Request request) {
        // 요청 데이터:  휴가 시작일, 휴가일, 휴가 종류 - 휴가 신청이 가능한 상태인지 체크

        List<ConfirmVacation> confirmVacations = confirmVacationRepository.findByMember(member);

        // 1.1 신청한 휴가일이 오늘날보다 과거인지 확인 - 과거면 예외
        if (LocalDate.now().isAfter(request.getStartDate())) {
            throw new CustomException(ErrorCode.IMPOSSIBLE_TO_APPLY_DATE);
        }
        // 2. 휴가 종료일 계산
        // 소수로 전달받은 휴가일수의 소숫점을 버리고, 휴가 시작일과 더하여 휴가 종류일을 계산함
        LocalDate endDate = request.getStartDate().plusDays((int) Math.floor(request.getAmount()));

        // 3. 이미 신청한 휴가시작일-휴가종료일과 겹치는지 확인
        // 3.1  직원의 ConfirmVacation 리스트를 가져온다

        //        List<ConfirmVacation> confirmVacations = member.getConfirmVacations();
        // 3.2 ConfirmVacationList에서 휴가 시작일-휴가 끝나는일 사이에 new 휴가시작일-휴가종료일이 겹치는지 체크
        // ConfirmVacationList 를 반복문을 돌려서 새 휴가신청이 기존 신청일과 겹치는지 체크
        // 메서드 구현 -  function (과거 confirmVacation, 신청_시작일, 신청_종료일)

        for (ConfirmVacation confirmVacation : confirmVacations) {

            if ((isVacationOverlaps(confirmVacation, request.getStartDate(), endDate))) {
                throw new CustomException(ErrorCode.IMPOSSIBLE_TO_APPLY_DATE); // 겹치면 예외처리
            }
        }

        // 4. DB에 저장
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

    // 기신청한 휴가종료일이 새로 신청한 휴가시작일보다 이전이 있고,
    // AND 기신청한 휴가시작일이 새로 신청한 휴가종료일보다 이후에 있으면 겹치지 않는다.
    // 조건을 만족하면 false 리턴
    public boolean isVacationOverlaps(
            ConfirmVacation confirmVacation, LocalDate applyStartDate, LocalDate applyEndDate) {
        LocalDate approvalStartDate = confirmVacation.getStartDate();
        LocalDate approvalEndDate = confirmVacation.getEndDate(); // 기신청한 휴가종료일
        // 새로 신청한 휴가시작일과 휴가종료일이 기존 휴가와 겹치는지 확인
        return !((approvalEndDate.isBefore(applyStartDate))
                || (approvalStartDate.isAfter(applyEndDate)));
    }
}
