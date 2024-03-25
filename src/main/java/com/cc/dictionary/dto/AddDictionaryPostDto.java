package com.cc.dictionary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AddDictionaryPostDto(

        @Schema(description = "글 제목", example = "제목입니다.")
        @NotBlank
        String title,

        @Schema(description = "글 내용", example = "내용입니다.")
        @NotBlank
        String content
) {
}
