package com.cc.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityVerify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 20)
    private String universityName;

    @Column(nullable = false, length = 20)
    private String major;

    @Column(nullable = false, length = 2)
    private String admissionYear;

    @Column(nullable = false)
    private String verifyImgUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
