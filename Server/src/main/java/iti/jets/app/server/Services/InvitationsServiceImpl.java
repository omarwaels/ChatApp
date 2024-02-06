package iti.jets.app.server.Services;

import iti.jets.app.server.Mappers.InvitationDtoMapper;
import iti.jets.app.server.Mappers.UserDtoMapper;
import iti.jets.app.server.db.*;
import iti.jets.app.server.models.entities.*;
import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.InvitationDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.Interfaces.server.InvitationService;
import iti.jets.app.shared.Interfaces.server.MailingService;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.enums.ModeEnum;
import iti.jets.app.shared.enums.StatusEnum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InvitationsServiceImpl extends UnicastRemoteObject implements InvitationService {
    public InvitationsServiceImpl() throws RemoteException {
    }

    MailingService mailingService = new MailingServiceImpl();

    @Override
    public List<InvitationDto> getUserRequests(int receiverId) throws RemoteException {
        InvitationDao invitationDao = new InvitationDao();
        List<Invitation> invitations = invitationDao.getAllInvitations(receiverId);
        return InvitationDtoMapper.invitationArrToInvitationDtoArr(invitations);
    }

    @Override
    public ChatDto acceptInvitation(InvitationDto invitationDto) throws RemoteException, SQLException {
        InvitationDao invitationDao = new InvitationDao();
        // add connection and delete the invitation
        int ret = invitationDao.acceptInvitation(InvitationDtoMapper.invitationDtoToInvitation(invitationDto));
        // create a new chat
        if (ret != 0) {
            ChatDao chatDao = new ChatDao();
            Chat chat = new Chat();
            chat.setChatName(Integer.toString(invitationDto.getInvitationID()));
            chat.setCreatedAt(getCurrentTimeStamp());
            chatDao.createPrivateChat(chat);
            int chatId = chatDao.getPrivateChatId(chat);
            if (chatId != -1) {
                ChatDto chatDto = new ChatDto();
                chatDto.setChatId(chatId);
                chatDto.setChatName(chat.getChatName());
                chatDto.setCreatedAt(chat.getCreatedAt());
                // add chat Participants
                addChatParticipant(chatDto, invitationDto.getSenderID());
                addChatParticipant(chatDto, invitationDto.getReceiverID());
                return chatDto;
            }
        }
        return null;
    }

    private Timestamp getCurrentTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime localDate = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = localDate.format(formatter);
        return Timestamp.valueOf(formattedTimestamp);
    }

    private void addChatParticipant(ChatDto chatDto, int userId) {
        ChatParticipantDao chatParticipantDao = new ChatParticipantDao();
        ChatParticipant chatParticipant = new ChatParticipant();
        chatParticipant.setChatId(chatDto.getChatId());
        chatParticipant.setParticipantId(userId);
        chatParticipant.setMemberSince(chatDto.getCreatedAt());
        chatParticipantDao.insert(chatParticipant);
    }

    @Override
    public int declineInvitation(InvitationDto invitationDto) throws RemoteException {
        InvitationDao invitationDao = new InvitationDao();
        return invitationDao.declineInvitation(InvitationDtoMapper.invitationDtoToInvitation(invitationDto));
    }

    @Override
    public List<Integer> sendInvitations(List<InvitationDto> invitationsDto) throws RemoteException {
        InvitationDao invitationDao = new InvitationDao();
        List<Integer> ret = new ArrayList<>();
        for (InvitationDto invitationDto : invitationsDto) {
            int receiverID = isUserExist(invitationDto.getReceiverPhone());
            if (receiverID == -1) {
                ret.add(1);
                continue;
            }
            invitationDto.setReceiverID(receiverID);
            if (invitationDto.getSenderID() == invitationDto.getReceiverID()) {
                ret.add(4);
                continue;
            }
            if (isAlreadyConnected(invitationDto.getSenderID(), invitationDto.getReceiverID())) {
                ret.add(2);
                continue;
            }
            if (isAlreadyInvited(invitationDto.getSenderID(), invitationDto.getReceiverID())) {
                ret.add(3);
                continue;
            }
            if (isInvitedByFriend(invitationDto.getSenderID(), invitationDto.getReceiverID())) {
                ret.add(5);
                continue;
            }
            boolean Success = invitationDao.insert(InvitationDtoMapper.invitationDtoToInvitation(invitationDto)) > 0;
            if (Success) {
                ret.add(0);
                new Thread(() -> {
                    try {
                        mailingService.sendMail(invitationDto.getReceiverID(), "Friend Request", "You have a friend request from " + invitationDto.getSenderName() + " " + invitationDto.getSenderPhone());
                        ServerService serverService = new ServerServiceImpl();
                        serverService.notifyFriendRequest(invitationDto.getReceiverID(), invitationDto.getSenderName(), invitationDto.getSenderPhone());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            } else {
                ret.add(6);
            }
        }
        return ret;
    }

    private int isUserExist(String receiverPhone) {
        UserDao userDao = new UserDao();
        User user = userDao.getById(receiverPhone);
        if (user == null)
            return -1;
        return user.getId();
    }

    private boolean isAlreadyConnected(int senderID, int receiverID) {
        ConnectionDao connectionDao = new ConnectionDao();
        Connection connection = connectionDao.getConnection(senderID, receiverID);
        return connection != null;
    }

    private boolean isAlreadyInvited(int senderID, int receiverID) {
        InvitationDao invitationDao = new InvitationDao();
        Invitation invitation = invitationDao.getInvitation(senderID, receiverID);
        return invitation != null;
    }

    private boolean isInvitedByFriend(int senderID, int receiverID) {
        InvitationDao invitationDao = new InvitationDao();
        Invitation invitation = invitationDao.getInvitation(receiverID, senderID);
        return invitation != null;
    }

    @Override
    public StatusEnum getUserStatusById(int id) throws RemoteException {
        UserDao userDao = new UserDao();
        User user = userDao.getByIntegerId(id);
        if (user != null) {
            return user.getStatus();
        }
        return null;
    }

    @Override
    public ModeEnum getUserModeById(int id) throws RemoteException {
        UserDao userDao = new UserDao();
        User user = userDao.getByIntegerId(id);
        if (user != null) {
            return user.getMode();
        }
        return null;
    }
}
