package com.cc.chat.domain;

import com.cc.member.domain.Member;

import java.io.Serializable;

public class ChatRoomMemberId implements Serializable {
    private ChatRoom chatRoom;
    private Member member;
}
