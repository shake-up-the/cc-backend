package com.cc.member.repository;

import com.cc.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByCustomId(String customId);

    boolean existsByCustomId(String customId);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
