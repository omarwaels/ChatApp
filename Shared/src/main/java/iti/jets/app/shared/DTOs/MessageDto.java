package iti.jets.app.shared.DTOs;


import java.io.Serializable;
import java.sql.Timestamp;

public class MessageDto implements Serializable {

    private Integer receiverId;
    private Integer chatId;
    private boolean containsFile;
    private String messageContent;
    private Timestamp sentAt;
    public MessageDto(Integer recieverId, Integer chatId, boolean containsFile, String messageContent, Timestamp sentAt) {
        this.receiverId = recieverId;
        this.chatId = chatId;
        this.containsFile = containsFile;
        this.messageContent = messageContent;
        this.sentAt = sentAt;
    }

    public Integer getSenderId() {
        return chatId;
    }

    public void setSenderId(Integer senderId) {
        this.chatId = chatId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
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

    @Override
    public String toString() {
        return "MessageDto{" +
                "chatId='" + chatId + '\'' +
                ", recieverId='" + receiverId + '\'' +
                ", containsFile=" + containsFile +
                ", messageContent='" + messageContent + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}
