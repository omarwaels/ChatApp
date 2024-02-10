package iti.jets.app.server.Services;

import iti.jets.app.server.Mappers.ChatDtoMapper;
import iti.jets.app.server.db.ChatDao;
import iti.jets.app.server.db.ChatParticipantDao;
import iti.jets.app.server.models.entities.Chat;
import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.Interfaces.server.CreateGroupService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class CreateGroupServiceImpl extends UnicastRemoteObject implements CreateGroupService {
    protected CreateGroupServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public int createGroup(ChatDto chatDto, List<Integer> membersIds) throws RemoteException {
        ChatDao chatDao = new ChatDao();
        Chat chat = ChatDtoMapper.chatDtoToChat(chatDto);
        int ret = chatDao.createGroupChat(chat);
        if (ret == 1) {
            int chatId = chatDao.getGroupChatId(chat);
            ChatParticipantDao chatParticipantDao = new ChatParticipantDao();
            ret = chatParticipantDao.addGroupParticipants(chatId, membersIds);
            if (ret > 0)
                return chatId;
            else
                return -1;
        } else
            return -1;
    }
}
