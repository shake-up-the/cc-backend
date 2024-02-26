package com.cc.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginDto (
        @Schema(description = "아이디", example = "test123")
        String customId,

        @Schema(description = "비밀번호", example = "qwer1234@")
        String password
) {
}
