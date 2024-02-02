package iti.jets.app.shared.Interfaces.server;

import iti.jets.app.shared.DTOs.InvitationDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InvitationService extends Remote {

    List<InvitationDto> getUserRequests (int receiverId) throws RemoteException;
    int acceptInvitation (InvitationDto invitationDto) throws RemoteException;

    int declineInvitation (InvitationDto invitationDto) throws RemoteException;
}
