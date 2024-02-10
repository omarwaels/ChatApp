package iti.jets.app.server.Services;

import iti.jets.app.server.db.ChatDao;
import iti.jets.app.server.db.ChatParticipantDao;
import iti.jets.app.server.db.ConnectionDao;
import iti.jets.app.server.db.MessageDao;
import iti.jets.app.server.models.entities.Connection;
import iti.jets.app.shared.Interfaces.server.DeleteChatsService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DeleteChatsServiceImpl extends UnicastRemoteObject implements DeleteChatsService {
    public DeleteChatsServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public int deletePrivateChat(int firstUserId, int secondUserId, int chatId) throws RemoteException {
        MessageDao messageDao = new MessageDao();
        ChatDao chatDao = new ChatDao();
        ChatParticipantDao chatParticipantDao = new ChatParticipantDao();
        ConnectionDao connectionDao = new ConnectionDao();


        int ret = messageDao.deleteMessagesByChatId(chatId);
        ret += chatParticipantDao.deleteChatParticipant(firstUserId, chatId);
        ret += connectionDao.delete(new Connection(firstUserId, secondUserId, null));
        ret += chatParticipantDao.deleteChatParticipant(secondUserId, chatId);
        ret += chatDao.delete(chatId);
        return ret;
    }

    @Override
    public int leaveGroupChat(int userId, int chatId) throws RemoteException {
        ChatParticipantDao chatParticipantDao = new ChatParticipantDao();
        return chatParticipantDao.deleteChatParticipant(userId, chatId);
    }
}
