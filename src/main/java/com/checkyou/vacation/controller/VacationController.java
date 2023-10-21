package com.checkyou.vacation.controller;

import com.checkyou.auth.entity.Member;
import com.checkyou.security.annotation.CurrentUser;
import com.checkyou.vacation.dto.Apply;
import com.checkyou.vacation.dto.Confirmation;
import com.checkyou.vacation.entity.ConfirmVacation;
import com.checkyou.vacation.service.VacationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/vacation")
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    // 휴가 신청
    @PostMapping(value = "/")
    public ResponseEntity<ConfirmVacation> apply(@CurrentUser Member member, @RequestBody @Valid Apply.Request request){

            return ResponseEntity.ok(vacationService.apply(member, request));

    }


    // 남은 휴가일 수 조회
    @GetMapping(value = "/rest")
    public Map<String, Float> getRestLeaves(@CurrentUser Member member){

        return vacationService.getRestLeaves(member);

    }
    @GetMapping
    public List<Confirmation.Response> confirmation(@CurrentUser Member member, @RequestParam int month){
        return vacationService.confirmation(member,month);
    }





}
