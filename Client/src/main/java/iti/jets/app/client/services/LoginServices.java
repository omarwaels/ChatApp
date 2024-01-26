package iti.jets.app.client.services;
import iti.jets.app.client.CallBack.ClientImpl;
import iti.jets.app.shared.DTOs.ConnectionDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.Interfaces.client.Client;
import iti.jets.app.shared.Interfaces.server.Connection;
import iti.jets.app.shared.Interfaces.server.Server;


import java.net.MalformedURLException;
import java.rmi.*;
public class LoginServices {

    public static UserDto login (UserLoginDto userLoginDto) throws RemoteException {
        Connection stub = null;
        try {
            stub = (Connection) Naming.lookup("rmi://localhost:8090/stub" );
            System.out.println("im here");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Client client = new ClientImpl() ;

        ConnectionDto connectionDto = new ConnectionDto(userLoginDto,client);
        System.out.println("Before Stub");
        return (stub.connect(connectionDto));
    }
}
