package com.cc.chat.dto;

import com.cc.chat.domain.ChatRoom;
import com.cc.chat.domain.ChatRoomMember;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRoomInfoDto {
    private String area;
    private String meetingDate;
    private String meetingPlace;
    private int memberNum;
    private List<MemberInfo> memberList = new ArrayList<>();

    @Data
    public static class MemberInfo {
        private String id;
        private String name;
        private String gender;
    }

    public ChatRoomInfoDto(ChatRoom chatRoom, List<ChatRoomMember> chatRoomMemberList) {
        this.area = chatRoom.getArea();
        this.meetingDate = chatRoom.getMeetingDate();
        this.meetingPlace = chatRoom.getMeetingPlace();
        this.memberNum = chatRoom.getMemberNum();
        for (ChatRoomMember chatRoomMember : chatRoomMemberList) {
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.id = chatRoomMember.getMember().getCustomId();
            memberInfo.name = chatRoomMember.getMember().getName();
            memberInfo.gender = chatRoomMember.getMember().getGender().toString();
            memberList.add(memberInfo);
        }
    }
}
