package iti.jets.app.shared.DTOs;


import java.io.Serializable;
import java.sql.Timestamp;

public class MessageDto implements Serializable {

    private String senderId;
    private String recieverId;

    private boolean containsFile;
    private String messageContent;
    private Timestamp sentAt;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
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
                "senderId='" + senderId + '\'' +
                ", recieverId='" + recieverId + '\'' +
                ", containsFile=" + containsFile +
                ", messageContent='" + messageContent + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}
