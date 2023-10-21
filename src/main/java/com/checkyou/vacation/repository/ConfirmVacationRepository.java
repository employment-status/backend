package com.checkyou.vacation.repository;

import com.checkyou.auth.entity.Member;
import com.checkyou.vacation.entity.ConfirmVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Month;
import java.util.List;

public interface ConfirmVacationRepository extends JpaRepository<ConfirmVacation, Long> {

}
