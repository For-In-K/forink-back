package com.forink.forink.chat.entity.dao;

import com.forink.forink.chat.entity.Chat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByMember_Id(Long memberId);
}
