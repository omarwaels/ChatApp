package iti.jets.app.server.Services;

import iti.jets.app.server.db.InvitationRequestDAO;
import iti.jets.app.shared.DTOs.UserInvitationDto;
import iti.jets.app.shared.Interfaces.server.InvitationService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class GetInvitationsServiceImpl extends UnicastRemoteObject implements InvitationService {
    public GetInvitationsServiceImpl() throws RemoteException {
    }

    @Override
    public List<UserInvitationDto> getUserRequests(int receiverId) throws RemoteException {
        InvitationRequestDAO dao = new InvitationRequestDAO();
        return dao.getInvitationsByReceiverId(receiverId);
    }

    @Override
    public boolean acceptInvitation(int invitationId, int receiverId) throws RemoteException {
        InvitationRequestDAO dao = new InvitationRequestDAO();
        return dao.acceptInvitation(invitationId,receiverId);
    }
}
