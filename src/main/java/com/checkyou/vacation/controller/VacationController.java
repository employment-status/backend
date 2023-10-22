package com.checkyou.vacation.controller;

import com.checkyou.auth.entity.Member;
import com.checkyou.security.annotation.CurrentUser;
import com.checkyou.vacation.dto.Apply;
import com.checkyou.vacation.service.VacationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
