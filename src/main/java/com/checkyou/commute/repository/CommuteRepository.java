package com.checkyou.commute.repository;

import com.checkyou.auth.entity.Member;
import com.checkyou.commute.entity.Commute;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuteRepository extends JpaRepository<Commute, Long> {
    boolean existsByMemberAndCreatedAtBetween(
            Member member, LocalDateTime startTime, LocalDateTime endTIme);
}
