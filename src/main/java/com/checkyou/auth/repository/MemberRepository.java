package com.checkyou.auth.repository;

import com.checkyou.auth.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByCode(String code);

    boolean existsByCode(String code);
}
