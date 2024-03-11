package com.cc.member.service;

import com.cc.auth.SecurityUtil;
import com.cc.exception.MemberNotFoundException;
import com.cc.member.domain.Member;
import com.cc.member.domain.UniversityVerify;
import com.cc.member.repository.MemberRepository;
import com.cc.member.repository.UniversityVerifyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UniversityVerifyService {

    private final MemberRepository memberRepository;
    private final UniversityVerifyRepository universityVerifyRepository;
    private final UniversityS3Service universityS3Service;

    @Transactional
    public void requestUniversityVerify(String universityName, String major, String admissionYear, MultipartFile verifyImage) throws IOException {
        Member member = memberRepository.findByCustomId(SecurityUtil.getLoginId())
                .orElseThrow(MemberNotFoundException::new);
        String verifyImgUrl = universityS3Service.upload(verifyImage);

        UniversityVerify universityVerify = UniversityVerify.builder()
                .member(member)
                .universityName(universityName)
                .major(major)
                .admissionYear(admissionYear)
                .verifyImgUrl(verifyImgUrl)
                .isConfirmed(false)
                .build();

        universityVerifyRepository.save(universityVerify);
    }
}
