package iti.jets.app.shared.Interfaces.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DeleteChatsService extends Remote {
    int deletePrivateChat(int firstUserId, int secondUserId, int chatId) throws RemoteException;

    int leaveGroupChat(int userId, int chatId) throws RemoteException;
}
