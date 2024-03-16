package com.cc.member.dto;

import com.cc.member.domain.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyInfoDto {

    private String name;
    private int age;
    private String phone;
    private String email;
    private String universityId;
    private String universityName;
    private String major;
    private boolean haveMeetingProfile;

    public MyInfoDto(Member member) {
        this.name = member.getName();
        this.age = LocalDateTime.now().getYear() - Integer.parseInt(member.getBirth().substring(0, 4)) + 1;
        this.phone = member.getPhone();
        this.email = member.getEmail();
        this.universityId = member.getUniversity().getId();
        this.universityName = member.getUniversity().getUniversityName();
        this.major = member.getUniversity().getMajor();
        this.haveMeetingProfile = member.getMeetingProfile() != null;
    }
}

