package iti.jets.app.client.CallBack;

import iti.jets.app.client.controllers.ChatScreenController;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.client.Client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {
    private final int id;

    ChatScreenController chatScreenController;

    public ClientImpl(ChatScreenController controller, int id) throws RemoteException {
        this.chatScreenController = controller;
        this.id = id;
    }

    @Override
    public void recieveMessage(MessageDto messageDto) {
        chatScreenController.receiveMessage(messageDto);
    }

    @Override
    public int getID() throws RemoteException {
        return id;
    }
}
