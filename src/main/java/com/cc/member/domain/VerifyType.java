package com.cc.member.domain;

public enum VerifyType {
    SIGNUP("회원가입"), FIND_ID("아이디 찾기"), FIND_PASSWORD("비밀번호 찾기");

    private final String name;

    VerifyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
