package iti.jets.app.shared.Interfaces.server;

import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.InvitationDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.enums.ModeEnum;
import iti.jets.app.shared.enums.StatusEnum;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface InvitationService extends Remote {

    List<InvitationDto> getUserRequests(int receiverId) throws RemoteException;

    ChatDto acceptInvitation(InvitationDto invitationDto) throws RemoteException, SQLException;

    int declineInvitation(InvitationDto invitationDto) throws RemoteException;

    List<Integer> sendInvitations(List<InvitationDto> invitationsDto) throws RemoteException;

    public StatusEnum getUserStatusById(int id) throws RemoteException;
    public ModeEnum getUserModeById(int id) throws RemoteException;
}
