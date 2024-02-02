package iti.jets.app.shared.Interfaces.server;

import iti.jets.app.shared.DTOs.UserInvitationDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InvitationService extends Remote {

    List<UserInvitationDto> getUserRequests (int receiverId) throws RemoteException;
    boolean acceptInvitation (int invitationId, int receiverId) throws RemoteException;
}
