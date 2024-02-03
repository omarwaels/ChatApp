package iti.jets.app.client.CallBack;

import iti.jets.app.client.controllers.ChatScreenController;
import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.client.Client;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientImpl extends UnicastRemoteObject implements Client {
    private final int id;

    ChatScreenController chatScreenController;

    public ClientImpl(ChatScreenController controller, int id) throws RemoteException {
        this.chatScreenController = controller;
        this.id = id;
    }

    @Override
    public void receiveMessage(MessageDto messageDto) {
        chatScreenController.receiveMessage(messageDto);
    }

    @Override
    public int getID() throws RemoteException {
        return id;
    }

    @Override
    public void updateFriendStatus(int friendId, boolean online) throws RemoteException {
        chatScreenController.updateFriendStatus(friendId, online);
    }

    @Override
    public void addGroup(ChatDto chatDto, List<Integer> membersId) throws IOException {
        chatScreenController.addNewGroupInContactList(chatDto, membersId);
    }

    @Override
    public void addChatForNewFriend(FriendInfoDto friendInfoDto, ChatDto chatDto) throws IOException {
        chatScreenController.addNewFriendInContactList(friendInfoDto, chatDto);
    }
}
