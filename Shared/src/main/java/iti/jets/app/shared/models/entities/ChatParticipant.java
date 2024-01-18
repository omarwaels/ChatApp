package iti.jets.app.shared.models.entities;

import java.sql.Timestamp;

public class ChatParticipant {
    private int chatId;
    private int participantId;
    private Timestamp memberSince;

    public ChatParticipant() {
    }

    public ChatParticipant(int chatId, int participantId, Timestamp memberSince) {
        this.chatId = chatId;
        this.participantId = participantId;
        this.memberSince = memberSince;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public Timestamp getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(Timestamp memberSince) {
        this.memberSince = memberSince;
    }
}
