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
            if (c.getID() == messageDto.getReceiverId()) {
                c.receiveMessage(messageDto);
                return;
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
}
