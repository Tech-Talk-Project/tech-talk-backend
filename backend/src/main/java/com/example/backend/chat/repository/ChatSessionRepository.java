package com.example.backend.chat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ChatSessionRepository {
    private final RedisTemplate<String, String> redisTemplate;
    public void saveSessionData(String sessionId, String userId, String chatRoomId) {
        redisTemplate.opsForHash().put(sessionId, "userId", userId);
        redisTemplate.opsForHash().put(sessionId, "chatRoomId", chatRoomId);
    }
    public Map<String, String> getSessionData(String sessionId) {
        Map<Object, Object> rawData = redisTemplate.opsForHash().entries(sessionId);
        Map<String, String> sessionData = new HashMap<>();
        sessionData.put("userId", (String) rawData.get("userId"));
        sessionData.put("chatRoomId", (String) rawData.get("chatRoomId"));
        return sessionData;
    }
}