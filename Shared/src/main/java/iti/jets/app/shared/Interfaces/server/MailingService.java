package iti.jets.app.shared.Interfaces.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MailingService extends Remote {
    public void sendMail(int id, String subject, String msg) throws RemoteException;
}
