package iti.jets.app.client.services;

import iti.jets.app.client.CallBack.ClientImpl;
import iti.jets.app.shared.DTOs.ChatScreenDto;
import iti.jets.app.shared.DTOs.ConnectionDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.Interfaces.client.Client;
import iti.jets.app.shared.Interfaces.server.Connection;
import iti.jets.app.shared.Interfaces.server.Server;


import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginServices {

    public static ChatScreenDto login(UserLoginDto userLoginDto) throws RemoteException {
        Connection connection = null;

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8090);
            connection = (Connection) registry.lookup("rmi://localhost:8090/connection");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
        ConnectionDto connectionDto = new ConnectionDto(userLoginDto);
        return (connection.connect(connectionDto));
    }
}
