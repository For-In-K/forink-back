package com.forink.forink.chat.entity.dao;

import com.forink.forink.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
