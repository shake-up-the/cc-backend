package com.cc.member.domain;

public enum DrinkingCapacity {
    CANT_DRINK("못 마심"), DIDNT_DRINK("안 마심"), ONE_BOTTLE("1병 이하"), ONE_HALF_BOTTLE("1.5병"),
    TWO_BOTTLE("두 병"), TWO_HALF_BOTTLE("2.5병"), THREE_BOTTLE("3병"), THREE_HALF_BOTTLE("3.5병 이상");

    private final String name;

    DrinkingCapacity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
