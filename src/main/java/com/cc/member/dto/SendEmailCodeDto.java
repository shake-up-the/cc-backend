package com.cc.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendEmailCodeDto(
        @Schema(description = "이메일", example = "test123@email.com")
        @NotBlank
        @Email
        String email
) {
}
