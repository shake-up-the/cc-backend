package com.cc.member.repository;

import com.cc.member.domain.MeetingProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingProfileRepository extends JpaRepository<MeetingProfile, String> {
}
