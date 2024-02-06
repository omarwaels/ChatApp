package iti.jets.app.client.CallBack;

import iti.jets.app.client.controllers.ChatScreenController;
import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.client.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
    public void readFile(MessageDto messageDto, byte[] bytes) throws RemoteException {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            Path downloadsFilePath = getPathOfDownloadsFileOnSys();
            FileChannel channel = FileChannel.open(Paths.get(downloadsFilePath.toString() + "\\" + messageDto.getMessageContent()),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            channel.write(byteBuffer);
            chatScreenController.receiveMessage(messageDto);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to send File!!");
        }
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
    public void updateFriendPhoto(int friendId, byte[] photo) throws RemoteException {
        chatScreenController.updateFriendPhoto(friendId, photo);
    }

    @Override
    public void updateFriendMode(int friendId, String mode) throws RemoteException {
        chatScreenController.updateFriendMode(friendId, mode);
    }

    @Override
    public void updateFriendName(int friendId, String newName) throws RemoteException {
        chatScreenController.updateFriendName(friendId, newName);
    }

    @Override
    public void addGroup(ChatDto chatDto, List<Integer> membersId) throws IOException {
        chatScreenController.addNewGroupInContactList(chatDto, membersId);
    }

    @Override
    public void addChatForNewFriend(FriendInfoDto friendInfoDto, ChatDto chatDto) throws IOException {
        chatScreenController.addNewFriendInContactList(friendInfoDto, chatDto);
    }

    private static Path getPathOfDownloadsFileOnSys() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, "Downloads");
    }

    @Override
    public void receiveAnnouncement(String subject, String body) throws RemoteException {
        chatScreenController.showServerAnnouncement(subject, body);
    }

    @Override
    public void receiveInvitationRequest(String name, String phoneNumber) throws RemoteException {
        chatScreenController.showInvitationAnnouncement("You have a friend request from " + name + " with phone number " + phoneNumber + " go to the invitations tab to check it");
    }

}
