package iti.jets.app.server.models.entities;


import java.sql.Timestamp;

public class Message {

    private final int messageId;


    private int senderId;
    private int chatId;
    private boolean containsFile;
    private String messageContent;
    private Timestamp sentAt;

    private int FontSize;
    private String FontColor;
    private String FontWeight;
    private String FontFamily;
    private String FontStyle;
    private Boolean Underline;


    public Message(int messageId) {
        this.messageId = messageId;
    }

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


    public int getFontSize() {
        return FontSize;
    }

    public void setFontSize(int fontSize) {
        FontSize = fontSize;
    }

    public String getFontColor() {
        return FontColor;
    }

    public void setFontColor(String fontColor) {
        FontColor = fontColor;
    }

    public String getFontWeight() {
        return FontWeight;
    }

    public void setFontWeight(String fontWeight) {
        FontWeight = fontWeight;
    }

    public String getFontFamily() {
        return FontFamily;
    }

    public void setFontFamily(String fontFamily) {
        FontFamily = fontFamily;
    }

    public String getFontStyle() {
        return FontStyle;
    }

    public void setFontStyle(String fontStyle) {
        FontStyle = fontStyle;
    }

    public Boolean getUnderline() {
        return Underline;
    }

    public void setUnderline(Boolean underline) {
        Underline = underline;
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
                ", FontSize=" + FontSize +
                ", FontColor='" + FontColor + '\'' +
                ", FontWeight='" + FontWeight + '\'' +
                ", FontFamily='" + FontFamily + '\'' +
                ", FontStyle='" + FontStyle + '\'' +
                ", Underline=" + Underline +
                '}';

    }
}
