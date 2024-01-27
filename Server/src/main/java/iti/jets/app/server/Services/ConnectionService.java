package iti.jets.app.server.Services;

import iti.jets.app.server.Mappers.ChatDtoMapper;
import iti.jets.app.server.Mappers.FriendInfoDtoMapper;
import iti.jets.app.server.Mappers.InvitationDtoMapper;
import iti.jets.app.server.Mappers.UserDtoMapper;
import iti.jets.app.server.db.ChatDao;
import iti.jets.app.server.db.ChatParticipantDao;
import iti.jets.app.server.db.InvitationDao;
import iti.jets.app.server.db.UserDao;
import iti.jets.app.server.models.entities.Chat;
import iti.jets.app.server.models.entities.Invitation;
import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.*;
import iti.jets.app.shared.Interfaces.client.Client;
import iti.jets.app.shared.Interfaces.server.Connection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectionService extends UnicastRemoteObject implements Connection {

    public HashMap<String, Client>  activeConnections = new HashMap<>();
    public ConnectionService() throws RemoteException {
    }

    @Override
    public ChatScreenDto connect(ConnectionDto connectionDto) throws RemoteException {
        UserDao userDao = new UserDao();
        InvitationDao invitationDao = new InvitationDao();
        ChatDao chatDao = new ChatDao();

        User userResult = userDao.getById(connectionDto.getUserLoginDto().getPhoneNumber());



        ChatParticipantDao chatParticipantDao = new ChatParticipantDao();



        if(userResult == null){
            return null;
        }
        activeConnections.put(connectionDto.getUserLoginDto().getPhoneNumber(),connectionDto.getClient() );

        UserDto userDtoResult = UserDtoMapper.UserToUserDto(userResult);

        HashMap <Integer, Integer> userFriendsAndChatIDs = chatParticipantDao.getUserFriendsAndChatIDs(userResult.getId());
        HashMap <User, Chat> userFriendsAndChatEntities = new HashMap<>();
        HashMap <FriendInfoDto, ChatDto> userFriendsAndChatDto = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : userFriendsAndChatIDs.entrySet()) {
            Integer userId = entry.getKey();
            Integer chatId = entry.getValue();
            User user = userDao.getByIntegerId(userId);
            Chat chat = chatDao.getById(chatId);
            userFriendsAndChatEntities.put(user, chat);
        }
        for (Map.Entry<User, Chat> entry : userFriendsAndChatEntities.entrySet()) {
            User user = entry.getKey();
            Chat chat = entry.getValue();
            FriendInfoDto friendInfoDto = FriendInfoDtoMapper.userToFriend(user);
            ChatDto chatDto = ChatDtoMapper.chatToChatDto(chat);
            userFriendsAndChatDto.put(friendInfoDto, chatDto);
        }
        ArrayList<Invitation> invitations = invitationDao.getAllInvitations(userResult.getId());
        ArrayList<InvitationDto> invitationDtos = InvitationDtoMapper.invitationArrToInvitationDtoArr(invitations);


        return new ChatScreenDto(userDtoResult,invitationDtos ,userFriendsAndChatDto  );

    }
}
