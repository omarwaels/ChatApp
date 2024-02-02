package iti.jets.app.shared.Interfaces.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceFactory extends Remote {
    public LoginService getLoginService() throws RemoteException;

    public RegisterService getRegisterService() throws RemoteException;

    public ServerService  getServerService() throws RemoteException;

    public InvitationService getInvitationService() throws RemoteException;

    public UpdateInfoService getUpdateInfoService() throws Exception;

    public CreateGroupService getCreateGroupService() throws RemoteException;

}
