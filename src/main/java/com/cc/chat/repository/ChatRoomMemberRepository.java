package com.cc.chat.repository;

import com.cc.chat.domain.ChatRoom;
import com.cc.chat.domain.ChatRoomMember;
import com.cc.chat.domain.ChatRoomMemberId;
import com.cc.member.domain.Gender;
import com.cc.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, ChatRoomMemberId> {

    boolean existsByMemberAndChatRoom(Member member, ChatRoom chatRoom);

    int countByChatRoomAndMember_Gender(ChatRoom chat, Gender gender);
}
