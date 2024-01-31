package iti.jets.app.server.Services;

import iti.jets.app.server.Mappers.ChatDtoMapper;
import iti.jets.app.server.Mappers.FriendInfoDtoMapper;
import iti.jets.app.server.Mappers.InvitationDtoMapper;
import iti.jets.app.server.Mappers.UserDtoMapper;
import iti.jets.app.server.db.*;
import iti.jets.app.server.models.entities.Chat;
import iti.jets.app.server.models.entities.Invitation;
import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.*;
import iti.jets.app.shared.Interfaces.client.Client;
import iti.jets.app.shared.Interfaces.server.LoginService;
import iti.jets.app.shared.enums.StatusEnum;
import iti.jets.app.shared.enums.UserEnum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginServiceImpl extends UnicastRemoteObject implements LoginService {
    public LoginServiceImpl() throws RemoteException {
    }

    @Override
    public LoginResultDto login(UserLoginDto userLoginDto) throws RemoteException {
        UserDao userDao = new UserDao();
        User userResult = userDao.getById(userLoginDto.getPhoneNumber());
        if (userResult == null || !userResult.getPassword().equals(userLoginDto.getPassword()))
            return null;

        userResult.setStatus(StatusEnum.ONLINE);
        // Get user
        UserDto userDtoResult = UserDtoMapper.UserToUserDto(userResult);
        // Get invitations
        ArrayList<InvitationDto> invitationsDto = getInvitationsDto(userResult);
        // Get friends ids
        ArrayList<Integer> friends = getFriendsIds(userResult.getId());
        // Get friends and chats of the user
        HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto = getFriendsAndChats(userDao, userResult, friends);

        return new LoginResultDto(userDtoResult, invitationsDto, userFriendsAndChatDto);
    }

    private HashMap<FriendInfoDto, ChatDto> getFriendsAndChats(UserDao userDao, User userResult, ArrayList<Integer> friends) {
        ChatParticipantDao chatParticipantDao = new ChatParticipantDao();
        HashMap<Integer, Integer> userFriendsAndChatIDs = chatParticipantDao.getUserFriendsAndChatIDs(userResult.getId(), friends);
        ChatDao chatDao = new ChatDao();
        HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : userFriendsAndChatIDs.entrySet()) {
            Integer userId = entry.getKey();
            Integer chatId = entry.getValue();
            FriendInfoDto user = FriendInfoDtoMapper.userToFriend(userDao.getByIntegerId(userId));
            ChatDto chat = ChatDtoMapper.chatToChatDto(chatDao.getById(chatId));
            userFriendsAndChatDto.put(user, chat);
        }
        return userFriendsAndChatDto;
    }

    private ArrayList<Integer> getFriendsIds(Integer userId) {
        ConnectionDao connectionDao = new ConnectionDao();
        return connectionDao.getAllConnections(userId);
    }

    private ArrayList<InvitationDto> getInvitationsDto(User userResult) {
        InvitationDao invitationDao = new InvitationDao();
        ArrayList<Invitation> invitations = invitationDao.getAllInvitations(userResult.getId());
        return InvitationDtoMapper.invitationArrToInvitationDtoArr(invitations);
    }
}
