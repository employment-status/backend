package com.checkyou.security;

import com.checkyou.auth.entity.Member;
import com.checkyou.auth.repository.MemberRepository;
import com.checkyou.global.exception.CustomException;
import com.checkyou.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        Member member =
                memberRepository
                        .findByCode(code)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        return new CustomUserDetails(member);
    }
}
