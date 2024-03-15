package com.cc.member.controller;

import com.cc.member.dto.MeetingProfileDto;
import com.cc.member.service.MeetingProfileService;
import com.cc.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "meeting-profile", description = "미팅 프로필 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting-profiles")
public class MeetingProfileController {

    private final MeetingProfileService meetingProfileService;

    @Operation(summary = "미팅 프로필 만들기")
    @PostMapping("/create")
    public Response createMeetingProfile(@Valid @RequestBody MeetingProfileDto meetingProfileDto) {
        meetingProfileService.createMeetingProfile(meetingProfileDto.mbti(), meetingProfileDto.drinkingCapacity(), meetingProfileDto.idealType(), meetingProfileDto.introduction());
        return Response.success();
    }

    @Operation(summary = "미팅 프로필 수정하기")
    @PutMapping("/update")
    public Response updateMeetingProfile(@Valid @RequestBody MeetingProfileDto meetingProfileDto) {
        meetingProfileService.updateMeetingProfile(meetingProfileDto.mbti(), meetingProfileDto.drinkingCapacity(), meetingProfileDto.idealType(), meetingProfileDto.introduction());
        return Response.success();
    }

    @Operation(summary = "내 미팅 프로필 조회하기")
    @GetMapping("/get")
    public Response getMyMeetingProfile() {
        MeetingProfileDto meetingProfileDto = meetingProfileService.getMyMeetingProfile();
        return Response.success(meetingProfileDto);
    }

    @Operation(summary = "미팅 프로필 조회하기")
    @GetMapping("/get/{memberId}")
    public Response getMeetingProfile(@PathVariable Long memberId) {
        MeetingProfileDto meetingProfileDto = meetingProfileService.getMeetingProfile(memberId);
        return Response.success(meetingProfileDto);
    }
}
