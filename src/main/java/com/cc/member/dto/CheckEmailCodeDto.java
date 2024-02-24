package com.cc.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CheckEmailCodeDto (

        @Schema(description = "이메일", example = "test123@email.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "인증코드", example = "1234")
        @NotBlank
        @Pattern(regexp = "^(\\d{4})$")
        String code
) {
}
