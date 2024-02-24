package com.cc.member.dto;

import com.cc.member.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupDto (

        @Schema(description = "아이디", example = "test123")
        @NotBlank
        String customId,

        @Schema(description = "이메일", example = "test123@email.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "비밀번호", example = "qwer1234@")
        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[0-9a-zA-Z!@#$%^&*]{8,16}$")
        String password,

        @Schema(description = "이름", example = "홍길동")
        @NotBlank
        String name,

        @Schema(description = "전화번호", example = "01012345678")
        @NotBlank
        @Pattern(regexp = "^(02|\\d{3})(\\d{4}|\\d{3})(\\d{4})$")
        String phone,

        @Schema(description = "성별", example = "M", allowableValues = {"M", "F"})
        Gender gender,

        @Schema(description = "생년월일", example = "19990101")
        @Size(min = 8, max = 8)
        String birth
) {
}
