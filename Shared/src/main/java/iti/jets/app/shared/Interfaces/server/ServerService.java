package iti.jets.app.shared.Interfaces.server;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.client.Client;

public interface ServerService extends Remote {
    void sendMessage(MessageDto messageDto) throws RemoteException;

    void sendFile(MessageDto messageDto, byte[] fileData) throws RemoteException;

    void register(Client c) throws RemoteException;

    void unregister(Client c) throws RemoteException;

    void updateStatus(ArrayList<Integer> friendsIds, int userId, boolean status) throws RemoteException;

    void updatePhoto(ArrayList<Integer> friendsIds, int userId, byte[] photo) throws RemoteException;

    void updateMode(ArrayList<Integer> friendsIds, int userId, String mode) throws RemoteException;

    void updateUserName(ArrayList<Integer> friendsIds, int userId, String newName) throws RemoteException;

    void addGroup(ChatDto chatDto, List<Integer> membersIds) throws IOException;

    void addChatForNewFriend(int receiverId, FriendInfoDto friendInfoDto, ChatDto chatDto) throws IOException;

    void sendAnnouncement(String subject, String body) throws RemoteException;

    void notifyFriendRequest(int receiverId, String name, String phoneNumber) throws RemoteException;

    void receiveAllOfflineMessages(int receiverId) throws RemoteException;

    boolean isAlreadyLoggedIn(int userId) throws RemoteException;

    CompletableFuture<Void> closeServer() throws RemoteException;

    void deleteFriend(int chatId, int friendId, int userId) throws RemoteException;
}
