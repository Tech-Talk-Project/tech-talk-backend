package com.example.backend.chat.controller;

import com.example.backend.chat.controller.dto.request.LastMessageRequestDto;
import com.example.backend.chat.controller.dto.request.MessagePageRequestDto;
import com.example.backend.chat.controller.dto.response.ChatMessageListDto;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.service.ChatMessageListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/chat/message")
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageListService chatMessageListService;

    @PostMapping("/cursor")
    public ResponseEntity<ChatMessageListDto> getMessagesByPage(@RequestBody MessagePageRequestDto requestDto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ChatRoom.LastMessage> messages = chatMessageListService.getChatMessageListAfterCursor(
                memberId, requestDto.getChatRoomId(), requestDto.getCursor());
        return ResponseEntity.ok(new ChatMessageListDto(messages));
    }
}
