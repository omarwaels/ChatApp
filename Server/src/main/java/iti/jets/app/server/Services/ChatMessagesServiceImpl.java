package iti.jets.app.server.Services;

import iti.jets.app.server.Mappers.MessageDtoMapper;
import iti.jets.app.server.db.ChatDao;
import iti.jets.app.server.db.ChatParticipantDao;
import iti.jets.app.server.db.MessageDao;
import iti.jets.app.server.db.UserDao;
import iti.jets.app.server.models.entities.Chat;
import iti.jets.app.server.models.entities.Message;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.server.ChatMessagesService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatMessagesServiceImpl extends UnicastRemoteObject implements ChatMessagesService {

    public ChatMessagesServiceImpl() throws RemoteException {
    }

    @Override
    public ArrayList<MessageDto> getChatMessages(int chatId) throws RemoteException {
        MessageDao messageDao = new MessageDao();
        ChatParticipantDao chatParticipantDao = new ChatParticipantDao();
        ChatDao chatDao = new ChatDao();
        Chat chat = chatDao.getById(chatId);
        ArrayList<Integer> receivers = chatParticipantDao.getChatParticipants(chatId);
        List<Message> messages = messageDao.getChatMessages(chatId);
        return covertToMessageDto(messages, receivers, chat.getAdminId() == 0);
    }

    @Override
    public int sendChatMessage(MessageDto messageDto) throws RemoteException {
        MessageDao messageDao = new MessageDao();
        return messageDao.insert(MessageDtoMapper.toMessage(messageDto));
    }

    private ArrayList<MessageDto> covertToMessageDto(List<Message> messages, ArrayList<Integer> participants, boolean singleChat) {
        ArrayList<MessageDto> messageDtos = new ArrayList<>();
        UserDao user = new UserDao();
        for (Message message : messages) {
            byte[] senderImage = user.getByIntegerId(message.getSenderId()).getPicture();
            messageDtos.add(MessageDtoMapper.toMessageDto(message, participants, senderImage, singleChat));
        }
        return messageDtos;
    }
}
