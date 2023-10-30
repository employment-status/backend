package com.checkyou.vacation.repository;

import com.checkyou.auth.entity.Member;
import com.checkyou.vacation.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    Vacation findByMember(Member member);
}
