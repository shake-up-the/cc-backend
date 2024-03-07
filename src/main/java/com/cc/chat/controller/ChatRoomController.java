package com.cc.chat.controller;

import com.cc.chat.service.ChatRoomService;
import com.cc.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "chat rooms", description = "채팅방 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "미팅방(채팅방) 개설하기")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createChatRoom(@RequestParam String area, @RequestParam int memberNum) {
        String chatRoomId = chatRoomService.createChatRoom(area, memberNum);
        return Response.success(chatRoomId);
    }
}
