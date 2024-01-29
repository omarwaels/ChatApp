package iti.jets.app.server.Network;

import iti.jets.app.server.Services.RegisterServiceImpl;
import iti.jets.app.server.Services.LoginServiceImpl;
import iti.jets.app.server.Services.ServerServiceImpl;
import iti.jets.app.shared.Interfaces.server.LoginService;
import iti.jets.app.shared.Interfaces.server.ServerService;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;


public class ServerConnection {
    private static Registry registry;

    public static void openConnection() {
        try {
            registry = LocateRegistry.createRegistry(8090);
            LoginService loginService = new LoginServiceImpl();
            ServerService serverService = new ServerServiceImpl();
            RegisterServiceImpl registerService = new RegisterServiceImpl();
            registry.rebind("LoginService", loginService);
            registry.rebind("ServerService", serverService);
            registry.rebind("RegisterService", registerService);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server is open now ....");
    }

    public static void closeConnection() {
        try {
            UnicastRemoteObject.unexportObject(registry, true);
            UnicastRemoteObject.unexportObject(registry, false);
            registry.unbind("ServerService");
            registry.unbind("LoginService");
            registry.unbind("RegisterService");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server Closed Successfully ..");
    }
}

