package com.cc.chat.dto;

import com.cc.chat.domain.Chat;
import lombok.Data;

@Data
public class ChatInfoDto {
    private String sender;
    private String message;

    public ChatInfoDto(Chat chat) {
        this.sender = chat.getSender();
        this.message = chat.getMessage();
    }
}
