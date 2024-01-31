package iti.jets.app.shared.DTOs;


import java.io.Serializable;
import java.sql.Timestamp;

public class MessageDto implements Serializable {

    private Integer senderId;
    private Integer receiverId;
    private Integer chatId;
    private boolean containsFile;
    private String messageContent;
    private Timestamp sentAt;

    public MessageDto() {
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public boolean isContainsFile() {
        return containsFile;
    }

    public void setContainsFile(boolean containsFile) {
        this.containsFile = containsFile;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    public MessageDto(Integer senderId, Integer receiverId, Integer chatId, boolean containsFile, String messageContent, Timestamp sentAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.chatId = chatId;
        this.containsFile = containsFile;
        this.messageContent = messageContent;
        this.sentAt = sentAt;
    }
}
