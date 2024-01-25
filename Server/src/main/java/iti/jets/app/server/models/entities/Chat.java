package iti.jets.app.server.models.entities;

import java.sql.Timestamp;

public class Chat {
    private int chatId;
    private byte[] chatImage;
    private String chatName;
    private Timestamp createdAt;
    private int adminId;

    public Chat() {
    }

    public Chat(int chatId, byte[] chatImage, String chatName, Timestamp createdAt, int adminId) {
        this.chatId = chatId;
        this.chatImage = chatImage;
        this.chatName = chatName;
        this.createdAt = createdAt;
        this.adminId = adminId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public byte[] getChatImage() {
        return chatImage;
    }

    public void setChatImage(byte[] chatImage) {
        this.chatImage = chatImage;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
