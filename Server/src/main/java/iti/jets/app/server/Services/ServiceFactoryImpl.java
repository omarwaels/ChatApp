package iti.jets.app.server.Services;

import iti.jets.app.shared.Interfaces.server.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceFactoryImpl extends UnicastRemoteObject implements ServiceFactory {

    public ServiceFactoryImpl() throws Exception {
        super();
    }

    @Override
    public LoginService getLoginService() throws RemoteException {
        return new LoginServiceImpl();
    }

    @Override
    public RegisterService getRegisterService() throws RemoteException {
        return new RegisterServiceImpl();
    }

    @Override
    public ServerService getServerService() throws RemoteException {
        return new ServerServiceImpl();
    }

    @Override
    public InvitationService getInvitationService() throws RemoteException {
        return new InvitationsServiceImpl();
    }

    @Override
    public UpdateInfoService getUpdateInfoService() throws RemoteException {
        return new UpdateInfoServiceImpl();
    }

    @Override
    public CreateGroupService getCreateGroupService() throws RemoteException {
        return new CreateGroupServiceImpl();
    }

    @Override
    public MailingService getMailingService() throws RemoteException {
        return new MailingServiceImpl();
    }


}
