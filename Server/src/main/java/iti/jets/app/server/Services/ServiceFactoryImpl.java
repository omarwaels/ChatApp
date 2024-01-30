package iti.jets.app.server.Services;

import iti.jets.app.shared.Interfaces.server.LoginService;
import iti.jets.app.shared.Interfaces.server.RegisterService;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;

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
}
