package com.cc.chat.controller;

import com.cc.auth.JwtTokenProvider;
import com.cc.chat.dto.ChatDto;
import com.cc.exception.MemberNotFoundException;
import com.cc.member.domain.Member;
import com.cc.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @MessageMapping(value = "/test")
    public void test(){
        log.debug("test");
    }

    @MessageMapping("/join")
    public void join(ChatDto chat, @Header("Authorization") String Authorization) {
        String memberId = jwtTokenProvider.getSubjectFromHeader(Authorization);
        Member member = memberRepository.findByCustomId(memberId).orElseThrow(MemberNotFoundException::new);
        chat.setType(ChatDto.MessageType.JOIN);
        chat.setSender(memberId);
        chat.setMessage(member.getName() + "님이 입장하셨습니다.");
        template.convertAndSend("/sub/room/" + chat.getRoomId(), chat);
    }

    @MessageMapping("/exit")
    public void exit(ChatDto chat, @Header("Authorization") String Authorization) {
        String memberId = jwtTokenProvider.getSubjectFromHeader(Authorization);
        Member member = memberRepository.findByCustomId(memberId).orElseThrow(MemberNotFoundException::new);
        chat.setType(ChatDto.MessageType.EXIT);
        chat.setSender(memberId);
        chat.setMessage(member.getName() + "님이 퇴장하셨습니다.");
        template.convertAndSend("/sub/room/" + chat.getRoomId(), chat);
    }

    @MessageMapping("/talk")
    public void talk(ChatDto chat, @Header("Authorization") String Authorization) {
        String memberId = jwtTokenProvider.getSubjectFromHeader(Authorization);
        chat.setType(ChatDto.MessageType.TALK);
        chat.setSender(memberId);
        template.convertAndSend("/sub/room/" + chat.getRoomId(), chat);
    }
}
