package com.cc.chat.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatDto implements Serializable {

    public enum MessageType{
        JOIN, TALK, EXIT
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}