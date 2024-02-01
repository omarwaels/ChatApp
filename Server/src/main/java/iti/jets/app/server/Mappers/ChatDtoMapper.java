package iti.jets.app.server.Mappers;

import iti.jets.app.server.models.entities.Chat;
import iti.jets.app.shared.DTOs.ChatDto;

import java.sql.Timestamp;

public class ChatDtoMapper {

    public static ChatDto chatToChatDto(Chat chat) {
        return new ChatDto(chat.getChatId(), chat.getChatImage(), chat.getChatName(), chat.getCreatedAt(), chat.getAdminId());
    }

    public static Chat chatDtoToChat(ChatDto chatDto) {
        return new Chat(chatDto.getChatId(), chatDto.getChatImage(), chatDto.getChatName(), chatDto.getCreatedAt(), chatDto.getAdminId());
    }
}
