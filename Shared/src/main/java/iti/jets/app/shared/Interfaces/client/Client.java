package iti.jets.app.shared.Interfaces.client;


import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.MessageDto;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Client extends Remote {
    void receiveMessage(MessageDto messageDto) throws RemoteException;

    void readFile(MessageDto messageDto, byte[] bytes) throws RemoteException;

    int getID() throws RemoteException;

    void updateFriendStatus(int friendId, boolean online) throws RemoteException;

    void updateFriendPhoto(int friendId, byte[] photo) throws RemoteException;

    void updateFriendMode(int friendId, String mode) throws RemoteException;

    void updateFriendName(int friendId, String newName) throws RemoteException;

    void addGroup(ChatDto chatDto, List<Integer> membersId) throws IOException;

    void addChatForNewFriend(FriendInfoDto friendInfoDto, ChatDto chatDto) throws IOException;

    void receiveAnnouncement(String subject, String msg) throws RemoteException;

    void receiveInvitationRequest(String name, String phoneNumber) throws RemoteException;
}
