package iti.jets.app.server.Network;
<<<<<<< HEAD

import iti.jets.app.server.Implementation.ConnectionsImpl;
import iti.jets.app.server.Implementation.RegisterServiceImpl;
=======
import iti.jets.app.server.Services.ConnectionService;
import iti.jets.app.server.Services.ServerService;
>>>>>>> 027b5f55f44c0b031cdeb961a7976d0e461f0ed8
import iti.jets.app.shared.Interfaces.server.Connection;
import iti.jets.app.shared.Interfaces.server.Server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

<<<<<<< HEAD

public class Connect {


    Registry registry;
=======
public class Connect {
        Registry registry ;
        public  void openConnection(){

            try {
                 registry = LocateRegistry.createRegistry(8090 );
                Connection connection = new ConnectionService();
                Server server = new ServerService();
                Naming.rebind("rmi://localhost:8090/connection", connection);
                Naming.rebind("rmi://localhost:8090/server", server);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Server is open now ....");
>>>>>>> 027b5f55f44c0b031cdeb961a7976d0e461f0ed8

    public void openConnection() {

        try {
            registry = LocateRegistry.createRegistry(8090);
            Connection stub = new ConnectionsImpl();
            RegisterServiceImpl registerService = new RegisterServiceImpl();
            Naming.rebind("rmi://localhost:8090/stub", stub);
            registry.rebind("RegisterService", registerService);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server is open now ....");


    }

    public void closeConnection() {
        try {

            UnicastRemoteObject.unexportObject(registry, true);
            UnicastRemoteObject.unexportObject(registry, false);
            Naming.unbind("rmi://localhost:8090/connection");
            Naming.unbind("rmi://localhost:8090/server");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Server Closed Successfully ..");
    }


}
