package iti.jets.app.shared.DTOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginResultDto implements Serializable {
    private UserDto userDto;
    private ArrayList<InvitationDto> invitationDto;
    private HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto;
    private HashMap<ChatDto, ArrayList<FriendInfoDto>> groupParticipants;



    public LoginResultDto() {
    }

    public LoginResultDto(UserDto userDto, ArrayList<InvitationDto> invitationDtos, HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto, HashMap<ChatDto, ArrayList<FriendInfoDto>> groupParticipants) {
        this.userDto = userDto;
        this.invitationDto = invitationDtos;
        this.userFriendsAndChatDto = userFriendsAndChatDto;
        this.groupParticipants = groupParticipants;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public ArrayList<InvitationDto> getInvitationDto() {
        return invitationDto;
    }

    public void setInvitationDto(ArrayList<InvitationDto> invitationDtos) {
        this.invitationDto = invitationDtos;
    }

    public HashMap<FriendInfoDto, ChatDto> getUserFriendsAndChatDto() {
        return userFriendsAndChatDto;
    }

    public void setUserFriendsAndChatDto(HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto) {
        this.userFriendsAndChatDto = userFriendsAndChatDto;
    }

    public HashMap<ChatDto, ArrayList<FriendInfoDto>> getGroupParticipants() {
        return groupParticipants;
    }

    public void setGroupParticipants(HashMap<ChatDto, ArrayList<FriendInfoDto>> groupParticipants) {
        this.groupParticipants = groupParticipants;
    }

    @Override
    public String toString() {
        return "LoginResultDto{" +
                "userDto=" + userDto +
                ", invitationDto=" + invitationDto +
                ", userFriendsAndChatDto=" + userFriendsAndChatDto +
                ", groupParticipants=" + groupParticipants +
                '}';
    }
}
