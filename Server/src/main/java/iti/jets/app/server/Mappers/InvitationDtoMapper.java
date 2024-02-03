package iti.jets.app.server.Mappers;

import iti.jets.app.server.db.UserDao;
import iti.jets.app.server.models.entities.Invitation;
import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.InvitationDto;

import java.util.ArrayList;
import java.util.List;

public class InvitationDtoMapper {

    public static InvitationDto invitationToInvitationDto(Invitation invitation) {
        UserDao userDao = new UserDao();
        User sender = userDao.getByIntegerId(invitation.getSenderID());
        User receiver = userDao.getByIntegerId(invitation.getReceiverID());
        return (new InvitationDto(invitation.getInvitationID(), invitation.getSenderID(), invitation.getReceiverID(), sender.getDisplayName(), sender.getPhoneNumber(), sender.getPicture(), receiver.getPhoneNumber()));
    }

    public static ArrayList<InvitationDto> invitationArrToInvitationDtoArr(List<Invitation> invitations) {
        ArrayList<InvitationDto> invitationDtos = new ArrayList<>();
        for (Invitation invitation : invitations) {
            invitationDtos.add(invitationToInvitationDto(invitation));
        }
        return invitationDtos;
    }

    public static Invitation invitationDtoToInvitation(InvitationDto invitationDto) {
        return new Invitation(invitationDto.getSenderID(), invitationDto.getReceiverID());
    }

}
