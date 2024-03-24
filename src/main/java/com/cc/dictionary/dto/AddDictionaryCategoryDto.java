package com.cc.dictionary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AddDictionaryCategoryDto(
        @Schema(description = "사전 카테고리 이름", example = "미팅 기본 예절")
        @NotBlank
        String title
) {
}
