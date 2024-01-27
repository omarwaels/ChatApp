package iti.jets.app.client.services;

import iti.jets.app.shared.Interfaces.server.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ConnectServerService  {

    private static Server server = null ;

    private ConnectServerService(){

    }
    public static Server getServer () throws MalformedURLException, NotBoundException, RemoteException {
        if(server == null){
            server = (Server) Naming.lookup("rmi://localhost:8090/server" );
        }
        return server;
    }
}
