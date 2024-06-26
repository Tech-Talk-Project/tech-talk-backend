package com.example.backend.chat.websocket;

import com.example.backend.chat.validation.ChatMemberValidator;
import com.example.backend.security.jwt.JwtClaimReader;
import com.example.backend.security.jwt.JwtValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatChannelInterceptor implements ChannelInterceptor {
    private final JwtClaimReader jwtClaimReader;
    private final JwtValidator jwtValidator;
    private final ChatMemberValidator chatMemberValidator;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String accessToken = accessor.getFirstNativeHeader("accessToken");
            jwtValidator.validateAccessToken(accessToken);

            WebSocketConnectionType type = WebSocketConnectionType.valueOf(accessor.getFirstNativeHeader("type"));
            if (type == WebSocketConnectionType.CHAT_ROOM ||
                type == WebSocketConnectionType.CHAT_ROOM_LIST) {
                Long memberId = jwtClaimReader.getMemberId(accessToken);
                String chatRoomId = accessor.getFirstNativeHeader("chatRoomId");
                chatMemberValidator.validateMember(chatRoomId, memberId);
                log.info("MemberId: " + memberId + " is validated for chatRoomId: " + chatRoomId);
            }
        }
        return message;
    }
}
