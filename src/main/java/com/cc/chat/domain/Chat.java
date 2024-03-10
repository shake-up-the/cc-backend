package com.cc.chat.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Getter
@Builder
@Document(collection = "chat_messages")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Chat {

    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String id;

    @Field("room_id")
    private String roomId;

    @Field("sender")
    private String sender;

    @Field("message")
    private String message;

    @Field("created_at")
    private LocalDateTime createdAt;
}
