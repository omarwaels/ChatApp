package iti.jets.app.shared.DTOs;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class MessageDto implements Serializable {

    private Integer senderId;
    private ArrayList<Integer> receiverId;
    private Integer chatId;
    private boolean containsFile;
    private String messageContent;
    private Timestamp sentAt;
    private byte[] senderImage;

    private boolean singleChat;
    private int fontSize;
    private String fontColor;
    private String fontWeight;
    private String fontFamily;
    private String fontStyle;
    private Boolean underline;

    public MessageDto(Integer senderId, ArrayList<Integer> receiverId, Integer chatId, boolean containsFile, String messageContent, Timestamp sentAt, byte[] senderImage, boolean singleChat) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.chatId = chatId;
        this.containsFile = containsFile;
        this.messageContent = messageContent;
        this.sentAt = sentAt;
        this.senderImage = senderImage;
        this.singleChat = singleChat;
    }


    public boolean isSingleChat() {
        return singleChat;
    }

    public void setSingleChat(boolean singleChat) {
        this.singleChat = singleChat;
    }

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

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public Boolean getUnderline() {
        return underline;
    }

    public void setUnderline(Boolean underline) {
        this.underline = underline;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", chatId=" + chatId +
                ", containsFile=" + containsFile +
                ", messageContent='" + messageContent + '\'' +
                ", sentAt=" + sentAt +
                ", singleChat=" + singleChat +
                '}';
    }
}
