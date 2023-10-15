package com.checkyou.auth.service;

import com.checkyou.auth.dto.LoginDto;
import com.checkyou.auth.entity.Member;
import com.checkyou.auth.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void login(LoginDto loginDto) {
        Member member =
                memberRepository
                        .findByCode(loginDto.getCode())
                        .orElseThrow(() -> new RuntimeException("유저없음"));

        member.setName("asihdiaosnflinadfo");
    }
}
