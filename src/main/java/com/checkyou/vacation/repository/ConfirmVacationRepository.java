package com.checkyou.vacation.repository;

import com.checkyou.auth.entity.Member;
import com.checkyou.vacation.entity.ConfirmVacation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmVacationRepository extends JpaRepository<ConfirmVacation, Long> {
    List<ConfirmVacation> findByMember(Member member);
}
