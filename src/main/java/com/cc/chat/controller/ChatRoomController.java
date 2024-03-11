package com.cc.chat.controller;

import com.cc.chat.dto.ChatInfoDto;
import com.cc.chat.dto.ChatRoomInfoDto;
import com.cc.chat.dto.CreateChatRoomDto;
import com.cc.chat.service.ChatRoomService;
import com.cc.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Response createChatRoom(@Valid @RequestBody CreateChatRoomDto createChatRoomDto) {
        String chatRoomId = chatRoomService.createChatRoom(createChatRoomDto.area(), createChatRoomDto.memberNum());
        return Response.success(chatRoomId);
    }

    @Operation(summary = "미팅방(채팅방)에 참여하기")
    @PostMapping("/join/{roomId}")
    public Response joinChatRoom(@PathVariable String roomId) {
        chatRoomService.addMember(roomId);
        return Response.success();
    }

    @Operation(summary = "미팅방(채팅방) 정보 불러오기")
    @GetMapping("/{roomId}")
    public Response getChatRoomInfo(@PathVariable String roomId) {
        ChatRoomInfoDto chatRoomInfoDto = chatRoomService.getChatRoomInfo(roomId);
        return Response.success(chatRoomInfoDto);
    }

    @Operation(summary = "미팅방(채팅방) 채팅 기록 불러오기")
    @GetMapping("/{roomId}/chats")
    public Response getChatHistory(@PathVariable String roomId,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "30") int size) {
        Page<ChatInfoDto> chatHistory = chatRoomService.getChatHistory(roomId, page, size);
        return Response.success(chatHistory);
    }
}
