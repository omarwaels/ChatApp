package iti.jets.app.server.Services;

import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.client.Client;
import iti.jets.app.shared.Interfaces.server.ServerService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerServiceImpl extends UnicastRemoteObject implements ServerService {
    private static ArrayList<Client> clients = new ArrayList<>();

    public ServerServiceImpl() throws RemoteException {
    }

    @Override
    public void sendMessage(MessageDto messageDto) throws RemoteException {
        for (Client c : clients) {
            System.out.println(c.getID() + " " + messageDto.getReceiverId());
            for(Integer userID : messageDto.getReceiverId()){
                if (c.getID() == userID) {
                    c.receiveMessage(messageDto);
                }
            }
        }
    }

    @Override
    public void register(Client c) throws RemoteException {
        clients.add(c);
    }

    @Override
    public void unregister(Client c) throws RemoteException {
        clients.remove(c);
    }

    @Override
    public void updateStatus(ArrayList<Integer> friendsIds, int userId, boolean online) throws RemoteException {
        for (Client c : clients) {
            if (friendsIds.contains(c.getID())) {
                c.updateFriendStatus(userId, online);
            }
        }
    }
}
