package iti.jets.app.server.Services;

import iti.jets.app.server.Mappers.InvitationDtoMapper;
import iti.jets.app.server.db.InvitationDao;
import iti.jets.app.server.models.entities.Invitation;
import iti.jets.app.shared.DTOs.InvitationDto;
import iti.jets.app.shared.Interfaces.server.InvitationService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class GetInvitationsServiceImpl extends UnicastRemoteObject implements InvitationService {
    public GetInvitationsServiceImpl() throws RemoteException {
    }

    @Override
    public List<InvitationDto> getUserRequests(int receiverId) throws RemoteException {
        InvitationDao invitationDao = new InvitationDao();
        List<Invitation> invitations = invitationDao.getAllInvitations(receiverId);
        return InvitationDtoMapper.invitationArrToInvitationDtoArr(invitations);
    }

    @Override
    public int acceptInvitation(InvitationDto invitationDto) throws RemoteException {
        InvitationDao invitationDao = new InvitationDao();
        return invitationDao.acceptInvitation(InvitationDtoMapper.invitationDtoToInvitation(invitationDto));
    }

    @Override
    public int declineInvitation(InvitationDto invitationDto) throws RemoteException {
        InvitationDao invitationDao = new InvitationDao();
        return invitationDao.declineInvitation(InvitationDtoMapper.invitationDtoToInvitation(invitationDto));
    }
}
