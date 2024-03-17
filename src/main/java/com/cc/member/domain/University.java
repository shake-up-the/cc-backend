package com.cc.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class University {

    @Id
    @Column(name = "university_id")
    private String id;

    @Column(nullable = false, length = 20)
    private String universityName;

    @Column(nullable = false, length = 20)
    private String major;

    @Column(nullable = false, length = 2)
    private String admissionYear;
}
