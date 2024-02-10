package iti.jets.app.server.Mappers;

import iti.jets.app.server.models.entities.Message;
import iti.jets.app.shared.DTOs.MessageDto;

import java.util.ArrayList;

public class MessageDtoMapper {
    public static MessageDto toMessageDto(Message message, ArrayList<Integer> receiverId, byte[] senderImage, boolean singleChat) {
        MessageDto messageDto = new MessageDto(message.getSenderId(), receiverId, message.getChatId(), message.isContainsFile(), message.getMessageContent(), message.getSentAt(), senderImage, singleChat);
        messageDto.setFontWeight(message.getFontWeight());
        messageDto.setFontSize(message.getFontSize());
        messageDto.setFontColor(message.getFontColor());
        messageDto.setFontFamily(message.getFontFamily());
        messageDto.setFontStyle(message.getFontStyle());
        messageDto.setUnderline(message.getUnderline());
        return messageDto;
    }

    public static Message toMessage(MessageDto messageDto) {
        Message message = new Message(-1, messageDto.getSenderId(), messageDto.getChatId(), messageDto.isContainsFile(), messageDto.getMessageContent(), messageDto.getSentAt());
        message.setFontWeight(messageDto.getFontWeight());
        message.setFontSize(messageDto.getFontSize());
        message.setFontColor(messageDto.getFontColor());
        message.setFontFamily(messageDto.getFontFamily());
        message.setFontStyle(messageDto.getFontStyle());
        message.setUnderline(messageDto.getUnderline());
        return message;
    }
}
