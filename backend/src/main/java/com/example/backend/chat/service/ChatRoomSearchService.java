package com.example.backend.chat.service;

import com.example.backend.chat.controller.dto.response.ChatRoomResponseDto;
import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.domain.ChatRoom;
import com.example.backend.chat.dto.ChatRoomByMemberDto;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.chat.repository.ChatRoomRepository;
import com.example.backend.entity.member.Member;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.repository.profile.MemberProfileRepository;
import com.example.backend.service.profile.dto.SimpleMemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomSearchService {
    private final ChatMemberRepository chatMemberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomValidator chatRoomValidator;
    private final ChatMessageListService chatMessageListService;

    private final Long ADMIN_ID = -1L;

    public ChatRoomResponseDto getChatRoom(Long memberId, String chatRoomId) {
        if (!chatRoomValidator.isMemberOfChatRoom(chatRoomId, memberId)) {
            throw new IllegalArgumentException("채팅방이 존재하지 않거나 참여하지 않은 채팅방입니다.");
        }
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);
        List<SimpleMemberProfileDto> simpleMemberProfileDtoList = getJoinedMembers(chatRoom.getJoinedMemberIds());
        Date lastAccessTime = getLastAccessTime(memberId, chatRoomId);
        List<ChatRoom.LastMessage> lastMessages = chatMessageListService.getChatMessageListBeforeCursor(memberId, chatRoomId, lastAccessTime);
        Integer unreadCount = lastMessages.size();
        if (lastMessages.size() < 100) {
            lastMessages = chatRoom.getLastMessages();
            unreadCount = getUnreadCount(lastMessages, lastAccessTime);
        }

        insertReadNotification(lastMessages, unreadCount);
        return new ChatRoomResponseDto(chatRoom.getTitle(), simpleMemberProfileDtoList, lastMessages, unreadCount);
    }

    private void insertReadNotification(List<ChatRoom.LastMessage> lastMessages, Integer unreadCount) {
        if (unreadCount > 0) {
            int lastMessageIndex = lastMessages.size() - unreadCount;
            ChatRoom.LastMessage message = new ChatRoom.LastMessage(ADMIN_ID, new Date(), "여기까지 읽었습니다.");
            lastMessages.add(lastMessageIndex, message);
        }
    }

    private Date getLastAccessTime(Long memberId, String chatRoomId) {
        ChatMember chatMember = chatMemberRepository.findById(memberId);
        for (ChatMember.JoinedChatRoom joinedChatRoom : chatMember.getJoinedChatRooms()) {
            if (joinedChatRoom.getChatRoomId().equals(chatRoomId)) {
                return joinedChatRoom.getLastAccessTime();
            }
        }
        throw new IllegalArgumentException("참여하지 않은 채팅방입니다.");
    }

    public List<ChatRoomByMemberDto> getChatRoomList(Long memberId) {
        ChatMember chatMember = chatMemberRepository.findById(memberId);
        List<ChatRoomByMemberDto> chatRoomByMemberDtoList = new ArrayList<>();

        for (ChatMember.JoinedChatRoom joinedChatRoom : chatMember.getJoinedChatRooms()) {
            ChatRoom chatRoom = chatRoomRepository.findById(joinedChatRoom.getChatRoomId());
            Date lastAccessTime = joinedChatRoom.getLastAccessTime();
            Integer unreadCount = getUnreadCount(chatRoom.getLastMessages(), lastAccessTime);
            chatRoomByMemberDtoList.add(
                    new ChatRoomByMemberDto(chatRoom, unreadCount));
        }

        chatRoomByMemberDtoList.sort((dto1, dto2) ->
                dto2.getLastMessage().getSendTime().compareTo(dto1.getLastMessage().getSendTime()));

        return chatRoomByMemberDtoList;
    }

    private List<SimpleMemberProfileDto> getJoinedMembers(List<Long> joinedMemberIds) {
        List<SimpleMemberProfileDto> simpleMemberProfileDtoList = new ArrayList<>();
        for (Long memberId : joinedMemberIds) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
            simpleMemberProfileDtoList.add(new SimpleMemberProfileDto(member));
        }
        return simpleMemberProfileDtoList;
    }

    private Integer getUnreadCount(List<ChatRoom.LastMessage> lastMessages, Date lastReadDate) {
        // binary search - upperbound
        int lo = 0;
        int hi = lastMessages.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (lastMessages.get(mid).getSendTime().after(lastReadDate)) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lastMessages.size() - lo;
    }

}
