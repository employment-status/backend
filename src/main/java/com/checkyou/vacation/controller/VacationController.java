package com.checkyou.vacation.controller;

import com.checkyou.auth.entity.Member;
import com.checkyou.security.annotation.CurrentUser;
import com.checkyou.vacation.dto.Apply;
import com.checkyou.vacation.dto.Confirmation;
import com.checkyou.vacation.dto.RestVacation;
import com.checkyou.vacation.service.VacationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/vacation")
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    // 휴가 신청
    @PostMapping
    public ResponseEntity<Void> apply(
            @CurrentUser Member member, @RequestBody @Valid Apply.Request request) {

        vacationService.apply(member, request);
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/rest")
    public ResponseEntity<RestVacation> restVacation(@CurrentUser Member member) {
        RestVacation restVacation = vacationService.restVacation(member);
        return ResponseEntity.ok(restVacation);
    }

    @GetMapping
    public ResponseEntity<List<Confirmation.Response>> confirmation(
            @CurrentUser Member member,
            @RequestParam int year,
            @RequestParam(required = false) Integer month) {
        List<Confirmation.Response> confirmations = vacationService.confirmation(member, year, month);
        return ResponseEntity.ok(confirmations);
    }
}
