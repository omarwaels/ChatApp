package iti.jets.app.shared.DTOs;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageDto implements Serializable {

    private Integer senderId;
    private ArrayList<Integer> receiverId;
    private Integer chatId;
    private boolean containsFile;
    private String messageContent;
    private Timestamp sentAt;
    private byte[] senderImage ;

    public MessageDto() {
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public ArrayList<Integer> getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(ArrayList<Integer> receiverId) {
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

    public byte[] getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(byte[] senderImage) {
        this.senderImage = senderImage;
    }

    public MessageDto(Integer senderId, ArrayList<Integer> receiverId, Integer chatId, boolean containsFile, String messageContent, Timestamp sentAt, byte[] senderImage) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.chatId = chatId;
        this.containsFile = containsFile;
        this.messageContent = messageContent;
        this.sentAt = sentAt;
        this.senderImage = senderImage;
    }
}
