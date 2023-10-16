package com.checkyou.security;

import com.checkyou.auth.entity.Member;
import com.checkyou.auth.type.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

    public Member member;

    public CustomUserDetails(Member member) {
        super(member.getCode(), "", authorities(member.getRole()));
        this.member = member;
    }

    private static Collection<? extends GrantedAuthority> authorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }
}
