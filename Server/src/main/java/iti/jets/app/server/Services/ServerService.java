package iti.jets.app.server.Services;

import iti.jets.app.server.Mappers.UserArrayMapper;
import iti.jets.app.server.db.ConnectionDao;
import iti.jets.app.server.db.UserDao;
import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.DTOs.UserFriendsDto;
import iti.jets.app.shared.Interfaces.client.Client;
import iti.jets.app.shared.Interfaces.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerService extends UnicastRemoteObject implements Server {
    private ArrayList<Client> clients = new ArrayList<>();

    public ServerService() throws RemoteException {
    }


    @Override
    public void sendMessage(MessageDto messageDto) throws RemoteException {
        for (Client c : clients) {
            if (c.getID() == messageDto.getReceiverId()) {
                c.recieveMessage(messageDto);
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
