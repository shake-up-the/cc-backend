package com.cc.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReissueTokenDto(

        @Schema(description = "액세스 토큰")
        String accessToken,

        @Schema(description = "리프레시 토큰")
        String refreshToken) {
}
