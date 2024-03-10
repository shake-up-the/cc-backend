package com.cc.chat.service;

import com.cc.auth.SecurityUtil;
import com.cc.chat.domain.ChatRoom;
import com.cc.chat.domain.ChatRoomMember;
import com.cc.chat.dto.ChatInfoDto;
import com.cc.chat.dto.ChatRoomInfoDto;
import com.cc.chat.repository.ChatRepository;
import com.cc.chat.repository.ChatRoomMemberRepository;
import com.cc.chat.repository.ChatRoomRepository;
import com.cc.exception.*;
import com.cc.member.domain.Member;
import com.cc.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRepository chatRepository;

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
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(ChatRoomNotFoundException::new);

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

    public ChatRoomInfoDto getChatRoomInfo(String roomId) {
        Member member = memberRepository.findByCustomId(SecurityUtil.getLoginId())
                .orElseThrow(MemberNotFoundException::new);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(ChatRoomNotFoundException::new);

        if(chatRoomMemberRepository.existsByMemberAndChatRoom(member, chatRoom)) {
            List<ChatRoomMember> chatRoomMemberList = chatRoomMemberRepository.findByChatRoom(chatRoom);
            return new ChatRoomInfoDto(chatRoom, chatRoomMemberList);
        } else {
            throw new NotInChatRoomException();
        }
    }

    public Page<ChatInfoDto> getChatHistory(String roomId, int page, int size) {
        Member member = memberRepository.findByCustomId(SecurityUtil.getLoginId())
                .orElseThrow(MemberNotFoundException::new);

        if(chatRoomMemberRepository.existsByMemberAndChatRoomId(member, roomId)) {
            Page<ChatInfoDto> chatHistory = chatRepository.findByRoomIdOrderByCreatedAtDesc(roomId, PageRequest.of(page, size)).map(ChatInfoDto::new);
            log.info("chatHistory: {}", chatHistory);
            return chatHistory;
        } else {
            throw new NotInChatRoomException();
        }
    }
}
