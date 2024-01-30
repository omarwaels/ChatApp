package iti.jets.app.server.Network;

import iti.jets.app.server.Services.RegisterServiceImpl;
import iti.jets.app.server.Services.LoginServiceImpl;
import iti.jets.app.server.Services.ServerServiceImpl;
import iti.jets.app.server.Services.ServiceFactoryImpl;
import iti.jets.app.shared.Interfaces.server.LoginService;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;


public class ServerConnection {
    private static Registry registry;

    public static void openConnection() throws Exception {
        try {
            registry = LocateRegistry.createRegistry(8090);
            ServiceFactory serviceFactory = new ServiceFactoryImpl();
            registry.rebind("ServiceFactory", serviceFactory);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server is open now ....");
    }

    public static void closeConnection() {
        try {
            UnicastRemoteObject.unexportObject(registry, true);
            UnicastRemoteObject.unexportObject(registry, false);
            registry.unbind("ServiceFactory");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server Closed Successfully ..");
    }
}

