package iti.jets.app.shared.Interfaces.server;

import iti.jets.app.shared.DTOs.ChatDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CreateGroupService extends Remote {
    int createGroup(ChatDto chatDto, List<Integer> membersIds) throws RemoteException;
}
