package com.cc.member.dto;

import com.cc.member.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FindIdByEmailDto(
        @Schema(description = "이메일", example = "test123@email.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "이메일 인증 디바이스 ID", example = "abcd1234")
        @NotBlank
        String emailDeviceId,

        @Schema(description = "이름", example = "홍길동")
        @NotBlank
        String name,

        @Schema(description = "성별", example = "M")
        @Enumerated(EnumType.STRING)
        Gender gender,

        @Schema(description = "생년월일", example = "19990101")
        @NotBlank
        @Size(min = 8, max = 8)
        String birth
) {
}
