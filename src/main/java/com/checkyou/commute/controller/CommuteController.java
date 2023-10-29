package com.checkyou.commute.controller;

import com.checkyou.auth.entity.Member;
import com.checkyou.commute.dto.CheckIn;
import com.checkyou.commute.dto.CheckOut;
import com.checkyou.commute.dto.CommuteDto;
import com.checkyou.commute.entity.Commute;
import com.checkyou.commute.service.CommuteService;
import com.checkyou.security.annotation.CurrentUser;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commute")
public class CommuteController {

    private final CommuteService commuteService;

    @PostMapping("/checkin")
    public ResponseEntity<Void> checkIn(
            @CurrentUser Member member, @RequestBody @Valid CheckIn.Request request) {

        commuteService.checkIn(member, request);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkOut(
            @CurrentUser Member member, @RequestBody @Valid CheckOut.Request request) {
        commuteService.checkOut(member, request);

        return ResponseEntity.ok(null);
    }

    @GetMapping
    public List<CommuteDto> getCommuteList(
            @CurrentUser Member member,
            @RequestParam int year,
            @RequestParam(required = false) Integer month) {

        List<Commute> commuteList = commuteService.getCommuteList(member, year, month);

        return commuteList.stream().map(CommuteDto::from).toList();
    }
}
