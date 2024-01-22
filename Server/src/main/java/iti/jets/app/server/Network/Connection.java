package iti.jets.app.server.Network;
import iti.jets.app.server.Implementation.ServerImpl;
import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.Interfaces.server.Server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;






public class Connection   {


        Registry registry ;
        public  void openConnection(){

            try {
                 registry = LocateRegistry.createRegistry(8090 );
                ServerImpl stub = new ServerImpl();
                Naming.rebind("rmi://localhost:8090/stub", stub);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Server is open now ....");
            System.out.println("hello");

        }
    public void closeConnection() {
        try {

            UnicastRemoteObject.unexportObject(registry, true);
            UnicastRemoteObject.unexportObject(registry, false);
            Naming.unbind("rmi://localhost:8090/stub");
        } catch (Exception e) {
            // Handle exceptions as needed
            e.printStackTrace();
        }

        System.out.println("Server Closed Successfully ..");
    }





}
