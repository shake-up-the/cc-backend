package com.cc.member.controller;

import com.cc.auth.TokenInfo;
import com.cc.member.domain.VerifyType;
import com.cc.member.dto.*;
import com.cc.member.service.EmailVerifyService;
import com.cc.member.service.MemberService;
import com.cc.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "members", description = "로그인/회원가입 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final EmailVerifyService emailVerifyService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Response signup(@Valid @RequestBody SignupDto signupDto) {
        memberService.checkDuplicateCustomId(signupDto.customId());
        memberService.checkDuplicateEmail(signupDto.email());
        memberService.checkDuplicatePhone(signupDto.phone());
        memberService.signup(signupDto.customId(), signupDto.email(), signupDto.password(), signupDto.name(), signupDto.phone(), signupDto.gender(), signupDto.birth());

        return Response.success();
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Response login(@RequestBody LoginDto loginDto) {
        TokenInfo tokenInfo = memberService.login(loginDto.customId(), loginDto.password());

        return Response.success(tokenInfo);
    }

    @Operation(summary = "이메일 인증 코드 전송")
    @PostMapping("/send-email-code")
    @ResponseStatus(HttpStatus.OK)
    public Response sendEmailCode(@RequestParam VerifyType type,
                                    @Valid @RequestBody SendEmailCodeDto sendEmailCodeDto) {
        if (type == VerifyType.SIGNUP) {
            memberService.checkDuplicateEmail(sendEmailCodeDto.email());
        }
        emailVerifyService.sendVerifyCode(type, sendEmailCodeDto.email());

        return Response.success();
    }

    @Operation(summary = "이메일 인증 코드 확인")
    @PostMapping("/check-email-code")
    @ResponseStatus(HttpStatus.OK)
    public Response checkEmailCode(@RequestParam VerifyType type,
                                   @Valid @RequestBody CheckEmailCodeDto checkEmailCodeDto) {
        String deviceId = emailVerifyService.checkVerifyCode(type, checkEmailCodeDto.email(), checkEmailCodeDto.code());

        return Response.success(deviceId);
    }
}
