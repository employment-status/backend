package com.checkyou.auth.controller;

import com.checkyou.auth.dto.LoginDto;
import com.checkyou.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/login")
    public void login(@RequestBody LoginDto loginDto) {
        memberService.login(loginDto);
    }
}
