package iti.jets.app.client.CallBack;

import iti.jets.app.client.controllers.ChatScreenController;
import iti.jets.app.server.Services.ServerServiceImpl;
import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.client.Client;
import iti.jets.app.shared.Interfaces.server.ServerService;

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
    public void sendAnnouncement(String message) throws RemoteException {
        ServerService serverService = null; 
        try {
            if (serverService != null) {
                serverService.sendAnnouncement(message);
            } else {
                System.err.println("Server service reference is null.");
            }
        } catch (RemoteException e) {
            // Handle RemoteException, e.g., log or display an error
            e.printStackTrace();
        }
    }
}
