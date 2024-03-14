package com.cc.member.dto;

import com.cc.member.domain.DrinkingCapacity;
import com.cc.member.domain.MBTI;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMeetingProfileDto (

        @Schema(description = "MBTI", example = "INFP")
        @Enumerated(EnumType.STRING)
        MBTI mbti,

        @Schema(description = "주량", example = "ONE_BOTTLE")
        @Enumerated(EnumType.STRING)
        DrinkingCapacity drinkingCapacity,

        @Schema(description = "이상형", example = "키가 크고 잘생긴 사람")
        @NotBlank
        @Size(max = 100)
        String idealType,

        @Schema(description = "한 줄 소개", example = "안녕하세요")
        @NotBlank
        @Size(max = 100)
        String introduction
) {
}
