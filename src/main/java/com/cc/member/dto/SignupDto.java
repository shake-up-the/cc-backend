package com.cc.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignupDto (
        @Schema(description = "이메일")
        String email,

        @Schema(description = "비밀번호")
        String password,

        @Schema(description = "이름")
        String name,

        @Schema(description = "전화번호", example = "01012345678")
        String phone,

        @Schema(description = "성별", example = "M")
        String gender,

        @Schema(description = "생년월일", example = "19990101")
        String birth
) {
}
