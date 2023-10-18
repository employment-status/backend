package com.checkyou.commute.controller;

import com.checkyou.auth.entity.Member;
import com.checkyou.commute.dto.CheckIn;
import com.checkyou.commute.entity.Commute;
import com.checkyou.commute.service.CommuteService;
import com.checkyou.security.annotation.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommuteController {

    private final CommuteService commuteService;

    /*
    출근시간, 사번 갖고있는 dto
     */
    @PostMapping("/commute")
    public ResponseEntity<Commute> checkIn(
            @CurrentUser Member member, @RequestBody @Valid CheckIn.Request request) {

        return ResponseEntity.ok(commuteService.checkIn(member, request));
    }
}
