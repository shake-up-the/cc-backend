package com.cc.member.controller;

import com.cc.member.dto.CreateMeetingProfileDto;
import com.cc.member.service.MeetingProfileService;
import com.cc.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "meeting-profile", description = "미팅 프로필 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting-profiles")
public class MeetingProfileController {

    private final MeetingProfileService meetingProfileService;

    @Operation(summary = "미팅 프로필 만들기")
    @PostMapping("/create")
    public Response createMeetingProfile(@Valid @RequestBody CreateMeetingProfileDto createMeetingProfileDto) {
        meetingProfileService.createMeetingProfile(createMeetingProfileDto.mbti(), createMeetingProfileDto.drinkingCapacity(), createMeetingProfileDto.idealType(), createMeetingProfileDto.introduction());
        return Response.success();
    }

}
