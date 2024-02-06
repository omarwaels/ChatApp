package iti.jets.app.server.Services;

import iti.jets.app.server.db.MailingDao;
import iti.jets.app.shared.Interfaces.server.MailingService;
import iti.jets.app.shared.utils.EmailHandler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MailingServiceImpl extends UnicastRemoteObject implements MailingService {

    protected MailingServiceImpl() throws RemoteException {
    }


    EmailHandler emailHandler = new EmailHandler();
    MailingDao mailingDao = new MailingDao();


    @Override
    public void sendMail(int id , String subject, String msg) throws RemoteException {
        String to = mailingDao.getEmailByUserId(id);
        emailHandler.sendEmail(to, subject, msg);
    }
}
