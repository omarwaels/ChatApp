package iti.jets.app.client.services;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.Interfaces.server.Server;


import java.net.MalformedURLException;
import java.rmi.*;
public class LoginServices {

    public static UserDto login (UserLoginDto userLoginDto) throws RemoteException {
        Server stub = null;
        try {
            stub = (Server) Naming.lookup("rmi://localhost:8090/stub" );
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return (stub.connect(userLoginDto));
    }
}
