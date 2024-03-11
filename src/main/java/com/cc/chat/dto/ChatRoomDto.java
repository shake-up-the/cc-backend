package com.cc.chat.dto;

import com.cc.chat.domain.ChatRoom;
import com.cc.chat.domain.ChatRoomMember;
import com.cc.member.domain.Gender;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoomDto {
    private String area;
    private int memberNum;
    private Set<MemberInfo> maleList = new HashSet<>();
    private Set<ChatRoomDto.MemberInfo> femaleList = new HashSet<>();

    @Data
    public static class MemberInfo {
        private String name;
    }

    public ChatRoomDto(ChatRoom chatRoom, Set<ChatRoomMember> chatRoomMemberList) {
        this.area = chatRoom.getArea();
        this.memberNum = chatRoom.getMemberNum();
        for (ChatRoomMember chatRoomMember : chatRoomMemberList) {
            ChatRoomDto.MemberInfo memberInfo = new ChatRoomDto.MemberInfo();
            memberInfo.name = chatRoomMember.getMember().getName();
            if (chatRoomMember.getMember().getGender().equals(Gender.M)) {
                maleList.add(memberInfo);
            } else {
                femaleList.add(memberInfo);
            }
        }
    }
}
