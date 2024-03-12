package com.cc.member.service;

import com.cc.auth.SecurityUtil;
import com.cc.exception.MemberNotFoundException;
import com.cc.member.domain.Member;
import com.cc.member.domain.University;
import com.cc.member.domain.UniversityVerify;
import com.cc.member.repository.MemberRepository;
import com.cc.member.repository.UniversityRepository;
import com.cc.member.repository.UniversityVerifyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UniversityVerifyService {

    private final MemberRepository memberRepository;
    private final UniversityVerifyRepository universityVerifyRepository;
    private final UniversityRepository universityRepository;
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
                .build();

        universityVerifyRepository.save(universityVerify);
    }

    @Transactional
    public void approveUniversityVerify(Long universityVerifyId) {
        UniversityVerify universityVerify = universityVerifyRepository.findById(universityVerifyId)
                .orElseThrow(IllegalArgumentException::new);

        University university = University.builder()
                .id(UUID.randomUUID().toString().substring(0, 8))
                .universityName(universityVerify.getUniversityName())
                .major(universityVerify.getMajor())
                .admissionYear(universityVerify.getAdmissionYear())
                .build();

        university = universityRepository.save(university);

        Member member = universityVerify.getMember();
        member.setUniversity(university);
        member.addRole("VERIFIED_USER");

        universityS3Service.delete(universityVerify.getVerifyImgUrl());
        universityVerifyRepository.delete(universityVerify);
    }
}
