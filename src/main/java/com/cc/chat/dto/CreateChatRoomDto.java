package com.cc.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateChatRoomDto(

        @Schema(description = "지역", example = "서울 신촌")
        @NotBlank
        String area,

        @Schema(description = "인원", example = "3")
        @Min(2)
        @Max(4)
        int memberNum
) {
}
