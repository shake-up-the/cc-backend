package com.cc.member.service;

import com.cc.auth.SecurityUtil;
import com.cc.exception.MemberNotFoundException;
import com.cc.member.domain.DrinkingCapacity;
import com.cc.member.domain.MBTI;
import com.cc.member.domain.MeetingProfile;
import com.cc.member.domain.Member;
import com.cc.member.repository.MeetingProfileRepository;
import com.cc.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingProfileService {

    private final MeetingProfileRepository meetingProfileRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createMeetingProfile(MBTI mbti, DrinkingCapacity drinkingCapacity, String idealType, String introduction) {
        Member member = memberRepository.findByCustomId(SecurityUtil.getLoginId())
                .orElseThrow(MemberNotFoundException::new);

        MeetingProfile meetingProfile = MeetingProfile.builder()
                .id(member.getId())
                .mbti(mbti)
                .drinkingCapacity(drinkingCapacity)
                .idealType(idealType)
                .introduction(introduction)
                .build();

        meetingProfile = meetingProfileRepository.save(meetingProfile);

        member.setMeetingProfile(meetingProfile);
    }
}
