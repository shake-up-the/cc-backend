package com.cc.chat.repository;

import com.cc.chat.domain.ChatRoom;
import com.cc.chat.domain.ChatRoomMember;
import com.cc.chat.domain.ChatRoomMemberId;
import com.cc.member.domain.Gender;
import com.cc.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, ChatRoomMemberId> {

    boolean existsByMemberAndChatRoom(Member member, ChatRoom chatRoom);
    boolean existsByMemberAndChatRoomId(Member member, String chatRoomId);

    int countByChatRoomAndMember_Gender(ChatRoom chat, Gender gender);

    List<ChatRoomMember> findByChatRoom(ChatRoom chatRoom);
}
