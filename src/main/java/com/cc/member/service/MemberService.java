package com.cc.member.service;

import com.cc.auth.JwtTokenProvider;
import com.cc.auth.SecurityUtil;
import com.cc.auth.TokenInfo;
import com.cc.exception.*;
import com.cc.member.domain.Gender;
import com.cc.member.domain.Member;
import com.cc.member.dto.MyInfoDto;
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
import java.util.Optional;
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

        redisTemplate.opsForValue().set("refresh-token:%s".formatted(customId), tokenInfo.refreshToken(), 14, TimeUnit.DAYS);

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

    public String findCustomIdByEmail(String email, String name, Gender gender, String birth) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        if (!member.getName().equals(name) || !member.getGender().equals(gender) || !member.getBirth().equals(birth)) {
            throw new InvalidMemberInfoException();
        }

        return member.getCustomId();
    }

    @Transactional
    public String findPasswordByEmail(String email, String name, String customId, Gender gender, String birth) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        if (!member.getName().equals(name) || !member.getCustomId().equals(customId) || !member.getGender().equals(gender) || !member.getBirth().equals(birth)) {
            throw new InvalidMemberInfoException();
        }

        String password = SecurityUtil.generatePassword(10);
        member.changePassword(passwordEncoder.encode(password));

        return password;
    }

    @Transactional
    public TokenInfo reissueToken(String accessToken, String refreshToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        Member member = memberRepository.findByCustomId(authentication.getName()).orElseThrow(MemberNotFoundException::new);

        if(!jwtTokenProvider.isValidToken(refreshToken)) {
            throw new InvalidTokenException();
        }

        String storeRefreshToken = Optional.ofNullable(redisTemplate.opsForValue().get("refresh-token:%s".formatted(member.getCustomId()))).orElseThrow(InvalidTokenException::new);
        if (!storeRefreshToken.equals(refreshToken)) {
            throw new InvalidTokenException();
        }

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        redisTemplate.opsForValue().set("refresh-token:%s".formatted(member.getCustomId()), tokenInfo.refreshToken(), 14, TimeUnit.DAYS);

        return tokenInfo;
    }

    public MyInfoDto getMyInfo() {
        Member member = memberRepository.findByCustomId(SecurityUtil.getLoginId())
                .orElseThrow(MemberNotFoundException::new);

        return new MyInfoDto(member);
    }

}
