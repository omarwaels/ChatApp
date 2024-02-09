package iti.jets.app.server.Services;

import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.client.Client;
import iti.jets.app.shared.Interfaces.server.ServerService;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;

public class ServerServiceImpl extends UnicastRemoteObject implements ServerService {
    private static CopyOnWriteArraySet<Client> clients = new CopyOnWriteArraySet<>();

    private ChatMessagesServiceImpl chatMessagesService = new ChatMessagesServiceImpl();

    private static ConcurrentHashMap<Integer, List<MessageDto>> offlineMessages = new ConcurrentHashMap<>();

    public ServerServiceImpl() throws RemoteException {
    }

    @Override
    public void sendMessage(MessageDto messageDto) throws RemoteException {
        chatMessagesService.sendChatMessage(messageDto);
        boolean online;
        for (Integer userID : messageDto.getReceiverId()) {
            online = false;
            for (Client c : clients) {
                if (c.getID() == userID) {
                    online = true;
                    c.receiveMessage(messageDto);
                    break;
                }
            }
            if (!online) {
                if (offlineMessages.containsKey(userID)) {
                    offlineMessages.get(userID).add(messageDto);
                } else {
                    List<MessageDto> messages = new ArrayList<>();
                    messages.add(messageDto);
                    offlineMessages.put(userID, messages);
                }
            }
        }
    }

    @Override
    public void sendFile(MessageDto messageDto, byte[] fileData) throws RemoteException {
        chatMessagesService.sendChatMessage(messageDto);
        for (Client c : clients) {
            for (Integer userID : messageDto.getReceiverId()) {
                if (c.getID() == userID) {
                    c.readFile(messageDto, fileData);
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
        System.out.println(clients.size());
        clients.remove(c);
        System.out.println(clients.size());
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
    public void updatePhoto(ArrayList<Integer> friendsIds, int userId, byte[] photo) throws RemoteException {
        for (Client c : clients) {
            if (friendsIds.contains(c.getID())) {
                c.updateFriendPhoto(userId, photo);
            }
        }
    }

    @Override
    public void updateMode(ArrayList<Integer> friendsIds, int userId, String mode) throws RemoteException {
        for (Client c : clients) {
            if (friendsIds.contains(c.getID())) {
                c.updateFriendMode(userId, mode);
            }
        }
    }

    @Override
    public void updateUserName(ArrayList<Integer> friendsIds, int userId, String newName) throws RemoteException {
        for (Client c : clients) {
            if (friendsIds.contains(c.getID())) {
                c.updateFriendName(userId, newName);
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

    @Override
    public void sendAnnouncement(String subject, String body) throws RemoteException {
        System.out.println("Sending notification to all clients");
        for (Client client : clients) {
            client.receiveAnnouncement(subject, body);
        }
    }

    @Override
    public void notifyFriendRequest(int receiverId, String name, String phoneNumber) throws RemoteException {
        for (Client c : clients) {
            if (c.getID() == receiverId) {
                c.receiveInvitationRequest(name, phoneNumber);
                return;
            }
        }
    }

    @Override
    public void receiveAllOfflineMessages(int receiverId) throws RemoteException {
        for (Client c : clients) {
            if (c.getID() == receiverId) {
                if (offlineMessages.containsKey(receiverId)) {
                    for (MessageDto messageDto : offlineMessages.get(receiverId)) {
                        c.receiveMessage(messageDto);
                    }
                    offlineMessages.remove(receiverId);
                }
            }
        }
    }

    @Override
    public boolean isAlreadyLoggedIn(int userId) throws RemoteException {
        for (Client c : clients) {
            if (c.getID() == userId) {
                c.receiveAnnouncement("Log in problem", "You are trying to log in from another device. Please logout from this device first.");
                return true;
            }
        }
        return false;
    }

    @Override
    public CompletableFuture<Void> closeServer() throws RemoteException {
        CompletableFuture<Void> future = new CompletableFuture<>();

        new Thread(() -> {
            try {
                for (Client c : clients) {
                    c.closeClient();
                }
                future.complete(null); // Complete the future when all clients have been closed
            } catch (RemoteException e) {
                future.completeExceptionally(e); // Complete the future exceptionally if an error occurs
            }
        }).start();

        return future;
    }

    @Override
    public void deleteFriend(int chatId, int friendId, int userId) throws RemoteException {
        for (Client c : clients) {
            if (c.getID() == friendId) {
                c.deleteFriend(chatId, userId);
                return;
            }
        }
    }
}


