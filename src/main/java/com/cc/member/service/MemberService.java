package com.cc.member.service;

import com.cc.auth.JwtTokenProvider;
import com.cc.auth.TokenInfo;
import com.cc.exception.MemberNotFoundException;
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
    public void signup(String email, String password, String name, String phone, String gender, String birth) {
        memberRepository.save(Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .gender(gender)
                .birth(birth)
                .roles(List.of("USER"))
                .build());
    }

    public TokenInfo login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication =
                authenticationManagerBuilder
                        .getObject()
                        .authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        redisTemplate.opsForValue().set("refresh-token:" + member.getEmail(), tokenInfo.refreshToken(), 14, TimeUnit.DAYS);

        return tokenInfo;
    }
}
