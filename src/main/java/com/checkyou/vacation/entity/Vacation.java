package com.checkyou.vacation.entity;

import com.checkyou.auth.entity.Member;
import com.checkyou.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vacation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @Column(columnDefinition = "float default 12.0")
    private float annualLeave;

    @Setter
    @Column(columnDefinition = "float default 12.0")
    private float sickLeave;

    @Setter
    @Column(columnDefinition = "float default 12.0")
    private float additionalLeave;
}
