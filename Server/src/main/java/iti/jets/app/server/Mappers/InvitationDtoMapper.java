package iti.jets.app.server.Mappers;

import iti.jets.app.server.models.entities.Invitation;
import iti.jets.app.shared.DTOs.InvitationDto;

import java.util.ArrayList;

public class InvitationDtoMapper {

    public  static InvitationDto invitationToInvitationDto (Invitation invitation){
        return (new InvitationDto(invitation.getInvitationID() , invitation.getSenderID(),invitation.getReceiverID()));
    }
    public  static ArrayList<InvitationDto> invitationArrToInvitationDtoArr (ArrayList<Invitation> invitations){
        ArrayList<InvitationDto> invitationDtos =new ArrayList<>();
        for(Invitation invitation :invitations){
            invitationDtos.add(invitationToInvitationDto(invitation));
        }
        return invitationDtos;
    }

}
