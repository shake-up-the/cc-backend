package com.cc.member.controller;

import com.cc.member.service.UniversityVerifyService;
import com.cc.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "university-verify", description = "대학 인증 요청 API")
@RestController
@RequiredArgsConstructor
public class UniversityVerifyController {

    private final UniversityVerifyService universityVerifyService;

    @Operation(summary = "대학 인증 요청")
    @PostMapping(value = "api/university-verify", consumes = "multipart/form-data", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Response requestUniversityVerify(@Schema(description = "학교", example = "연세대학교(신촌)") @RequestParam(value = "universityName") String universityName,
                                            @Schema(description = "전공", example = "컴퓨터과학과") @RequestParam(value = "major") String major,
                                            @Schema(description = "학번", example = "21") @RequestParam(value = "admissionYear") String admissionYear,
                                            @RequestParam(value = "verifyImage") MultipartFile verifyImage) throws IOException {
        universityVerifyService.requestUniversityVerify(universityName, major, admissionYear, verifyImage);
        return Response.success();
    }

    @Operation(summary = "대학 인증 요청 승인")
    @PostMapping(value = "admin/university-verify/approve")
    @ResponseStatus(HttpStatus.CREATED)
    public Response approveUniversityVerify(@Schema(description = "대학 인증 요청 ID", example = "1") @RequestParam(value = "universityVerifyId") Long universityVerifyId) {
        universityVerifyService.approveUniversityVerify(universityVerifyId);
        return Response.success();
    }
}
