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
public class MeetingProfile {

    @Id
    @Column(name = "meeting_profile_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MBTI mbti;

    @Enumerated(EnumType.STRING)
    private DrinkingCapacity drinkingCapacity;

    @Column(nullable = false, length = 100)
    private String idealType;

    @Column(nullable = false, length = 100)
    private String introduction;

    public void setMbti(MBTI mbti) {
        this.mbti = mbti;
    }

    public void setDrinkingCapacity(DrinkingCapacity drinkingCapacity) {
        this.drinkingCapacity = drinkingCapacity;
    }

    public void setIdealType(String idealType) {
        this.idealType = idealType;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
