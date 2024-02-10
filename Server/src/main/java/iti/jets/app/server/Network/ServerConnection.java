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
            registry = LocateRegistry.createRegistry(8189);
            ServiceFactory serviceFactory = new ServiceFactoryImpl();
            registry.rebind("ServiceFactory", serviceFactory);


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            try {
                UnicastRemoteObject.unexportObject(registry, true);
            } catch (NoSuchObjectException e) {

            }
            try {
                UnicastRemoteObject.unexportObject(registry, false);
            } catch (NoSuchObjectException e) {

            }
            registry.unbind("ServiceFactory");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

