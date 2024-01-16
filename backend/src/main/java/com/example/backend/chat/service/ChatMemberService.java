package com.example.backend.chat.service;

import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMemberService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;



    public void exitChatRoom(String chatRoomId, Long memberId) {
        chatRoomRepository.pullMemberIdFromJoinedMemberIds(chatRoomId, memberId);
        chatMemberRepository.pullJoinedChatRoomFromJoinedChatRooms(memberId, chatRoomId);
    }
}
