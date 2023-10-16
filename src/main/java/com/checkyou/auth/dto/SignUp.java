package com.checkyou.auth.dto;

import com.checkyou.auth.entity.Member;
import com.checkyou.auth.type.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SignUp {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotBlank(message = "사번을 작성해주세요.")
        private String code;

        @Size(min = 8, max = 20, message = "비밀번호는 8 ~ 20의 길이 여야만 합니다")
        private String password;

        @NotBlank(message = "이름을 작성해주세요.")
        private String name;

        @NotBlank(message = "전화번호를 작성해주세요.")
        @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
        private String phoneNumber;

        @NotNull(message = "성별을 선택해주세요")
        private Gender gender;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @NotNull(message = "입사일을 작성해주세요. yyyy-MM-dd")
        private LocalDate joinDate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long id;

        private String code;

        private String name;

        private String phoneNumber;

        private Gender gender;

        private LocalDate joinDate;

        public static SignUp.Response from(Member member) {
            return Response.builder()
                    .id(member.getId())
                    .code(member.getCode())
                    .name(member.getName())
                    .phoneNumber(member.getPhoneNumber())
                    .gender(member.getGender())
                    .joinDate(member.getJoinDate())
                    .build();
        }
    }
}
