package com.cc.chat.repository;

import com.cc.chat.domain.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
    Page<Chat> findByRoomIdOrderByCreatedAtDesc(String roomId, Pageable pageable);
}
