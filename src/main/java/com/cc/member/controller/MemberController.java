package com.cc.member.controller;


import com.cc.member.dto.SignupDto;
import com.cc.member.service.MemberService;
import com.cc.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "members", description = "로그인/회원가입 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Response signup(@RequestBody SignupDto signupDto) {
        memberService.signup(signupDto.email(), signupDto.password(), signupDto.name(), signupDto.phone(), signupDto.gender(), signupDto.birth());

        return Response.success();
    }
}
