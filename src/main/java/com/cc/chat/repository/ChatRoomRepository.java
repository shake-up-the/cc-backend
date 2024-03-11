package com.cc.chat.repository;

import com.cc.chat.domain.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    Page<ChatRoom> findByOrderByCreatedAtDesc(Pageable pageable);
}
