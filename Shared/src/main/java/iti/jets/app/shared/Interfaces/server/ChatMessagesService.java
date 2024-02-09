package iti.jets.app.shared.Interfaces.server;

import iti.jets.app.shared.DTOs.MessageDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ChatMessagesService extends Remote {
    ArrayList<MessageDto> getChatMessages(int chatId) throws RemoteException;

    int sendChatMessage(MessageDto messageDto) throws RemoteException;
}
