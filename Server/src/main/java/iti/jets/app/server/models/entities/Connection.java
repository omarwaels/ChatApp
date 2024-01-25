package iti.jets.app.server.models.entities;

import java.sql.Timestamp;

public class Connection {
    private int firstUserId;
    private int secondUserId;
    private Timestamp connectedSince;

    public Connection(int firstUserId, int secondUserId, Timestamp connectedSince) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.connectedSince = connectedSince;
    }

    public int getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(int firstUserId) {
        this.firstUserId = firstUserId;
    }

    public int getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(int secondUserId) {
        this.secondUserId = secondUserId;
    }

    public Timestamp getConnectedSince() {
        return connectedSince;
    }

    public void setConnectedSince(Timestamp connectedSince) {
        this.connectedSince = connectedSince;
    }
}