package iti.jets.app.server.Services;

import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.client.Client;
import iti.jets.app.shared.Interfaces.server.ServerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerServiceImpl extends UnicastRemoteObject implements ServerService {
    private static ArrayList<Client> clients = new ArrayList<>();

    public ServerServiceImpl() throws RemoteException {
    }

    @Override
    public void sendMessage(MessageDto messageDto) throws RemoteException {
        for (Client c : clients) {
            for (Integer userID : messageDto.getReceiverId()) {
                if (c.getID() == userID) {
                    c.receiveMessage(messageDto);
                }
            }
        }
    }

    @Override
    public void sendFile(MessageDto messageDto , byte[] fileData) throws RemoteException {
        for (Client c : clients) {
            for (Integer userID : messageDto.getReceiverId()) {
                if (c.getID() == userID) {
                    c.readFile(messageDto , fileData);
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

    @Override
    public void addGroup(ChatDto chatDto, List<Integer> membersIds) throws IOException {
        for (Client c : clients) {
            if (membersIds.contains(c.getID()))
                c.addGroup(chatDto, membersIds);
        }
    }

    @Override
    public void addChatForNewFriend(int receiverId, FriendInfoDto friendInfoDto, ChatDto chatDto) throws IOException {
        for (Client c : clients) {
            if (c.getID() == receiverId) {
                c.addChatForNewFriend(friendInfoDto, chatDto);
                return;
            }
        }
    }
}
