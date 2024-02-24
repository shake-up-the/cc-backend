package com.cc.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginDto (
        @Schema(description = "이메일")
        String email,

        @Schema(description = "비밀번호")
        String password
) {
}
