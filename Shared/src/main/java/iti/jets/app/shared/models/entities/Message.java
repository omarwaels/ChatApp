package iti.jets.app.shared.models.entities;


import java.sql.Timestamp;
public class Message {

    private final int messageId ;



    private int senderId;
    private int chatId;
    private boolean containsFile;
    private String messageContent;
    private Timestamp sentAt;



    public Message(int messageId, int senderId, int chatId, boolean containsFile, String messageContent, Timestamp sentAt) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.chatId = chatId;
        this.containsFile = containsFile;
        this.messageContent = messageContent;
        this.sentAt = sentAt;
    }

    public int getMessageId() {
        return messageId;
    }



    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
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

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", senderId=" + senderId +
                ", chatId=" + chatId +
                ", containsFile=" + containsFile +
                ", messageContent='" + messageContent + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}
