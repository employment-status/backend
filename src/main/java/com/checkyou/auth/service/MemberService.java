package com.checkyou.auth.service;

import com.checkyou.auth.dto.Login;
import com.checkyou.auth.dto.SignUp.Request;
import com.checkyou.auth.entity.Member;
import com.checkyou.auth.repository.MemberRepository;
import com.checkyou.auth.type.Role;
import com.checkyou.global.exception.CustomException;
import com.checkyou.global.exception.ErrorCode;
import com.checkyou.security.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public Member signUp(Request request) {

        if (memberRepository.existsByCode(request.getCode())) {
            throw new CustomException(ErrorCode.ALREADY_SIGNUP);
        }

        String password = passwordEncoder.encode(request.getPassword());

        return memberRepository.save(
                Member.builder()
                        .code(request.getCode())
                        .password(password)
                        .name(request.getName())
                        .gender(request.getGender())
                        .phoneNumber(request.getPhoneNumber())
                        .role(Role.ROLE_MEMBER)
                        .joinDate(request.getJoinDate())
                        .build());
    }

    @Transactional
    public String login(Login request) {
        Member member =
                memberRepository
                        .findByCode(request.getCode())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.FAILED_LOGIN);
        }

        return tokenProvider.generateToken(member.getCode(), member.getId(), member.getRole());
    }
}
