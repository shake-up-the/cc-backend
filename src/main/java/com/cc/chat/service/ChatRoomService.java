package com.cc.chat.service;

import com.cc.auth.SecurityUtil;
import com.cc.chat.domain.ChatRoom;
import com.cc.chat.domain.ChatRoomMember;
import com.cc.chat.repository.ChatRoomMemberRepository;
import com.cc.chat.repository.ChatRoomRepository;
import com.cc.exception.AlreadyInChatRoomException;
import com.cc.exception.MaximumMemberReachedException;
import com.cc.exception.MemberNotFoundException;
import com.cc.member.domain.Member;
import com.cc.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Transactional
    public String createChatRoom(String area, int memberNum) {
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder()
                .area(area)
                .memberNum(memberNum)
                .build());

        addMember(chatRoom.getId());

        return chatRoom.getId();
    }

    @Transactional
    public void addMember(String roomId) {
        Member member = memberRepository.findByCustomId(SecurityUtil.getLoginId())
                .orElseThrow(MemberNotFoundException::new);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();

        if(chatRoomMemberRepository.existsByMemberAndChatRoom(member, chatRoom)) {
            throw new AlreadyInChatRoomException();
        }

        long memberCount = chatRoomMemberRepository.countByChatRoomAndMember_Gender(chatRoom, member.getGender());

        if (memberCount >= chatRoom.getMemberNum()) {
            throw new MaximumMemberReachedException();
        }

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.save(new ChatRoomMember(chatRoom, member));
        member.addChatRoom(chatRoomMember);
        chatRoom.addMember(chatRoomMember);
    }
}
