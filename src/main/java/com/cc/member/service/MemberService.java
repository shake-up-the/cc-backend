package com.cc.member.service;

import com.cc.auth.JwtTokenProvider;
import com.cc.auth.TokenInfo;
import com.cc.exception.*;
import com.cc.member.domain.Gender;
import com.cc.member.domain.Member;
import com.cc.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(String customId, String email, String password, String name, String phone, Gender gender, String birth) {

        memberRepository.save(Member.builder()
                .customId(customId)
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .gender(gender)
                .birth(birth)
                .roles(List.of("USER"))
                .build());
    }

    public TokenInfo login(String customId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(customId, password);

        Authentication authentication =
                authenticationManagerBuilder
                        .getObject()
                        .authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        Member member = memberRepository.findByCustomId(customId)
                .orElseThrow(MemberNotFoundException::new);

        redisTemplate.opsForValue().set("refresh-token:" + member.getCustomId(), tokenInfo.refreshToken(), 14, TimeUnit.DAYS);

        return tokenInfo;
    }

    public void checkDuplicateCustomId(String customId) {
        if (memberRepository.existsByCustomId(customId)) {
            throw new MemberCustomIdAlreadyExistsException();
        }
    }

    public void checkDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberEmailAlreadyExistsException();
        }
    }

    public void checkDuplicatePhone(String phone) {
        if (memberRepository.existsByPhone(phone)) {
            throw new MemberPhoneAlreadyExistsException();
        }
    }
}
