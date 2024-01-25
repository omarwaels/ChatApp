package iti.jets.app.server.models.entities;

public class Media {
    private int fileId;
    private int messageId;
    private byte[] fileContent;

    public Media() {
    }

    public Media(int fileId, int messageId, byte[] fileContent) {
        this.fileId = fileId;
        this.messageId = messageId;
        this.fileContent = fileContent;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }
}
