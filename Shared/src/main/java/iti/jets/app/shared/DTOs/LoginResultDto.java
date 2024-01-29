package iti.jets.app.shared.DTOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginResultDto implements Serializable {
    private UserDto userDto;
    private ArrayList<InvitationDto> invitationDtos;
    private HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto;

    public LoginResultDto() {
    }

    public LoginResultDto(UserDto userDto, ArrayList<InvitationDto> invitationDtos, HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto) {
        this.userDto = userDto;
        this.invitationDtos = invitationDtos;
        this.userFriendsAndChatDto = userFriendsAndChatDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public ArrayList<InvitationDto> getInvitationDtos() {
        return invitationDtos;
    }

    public void setInvitationDtos(ArrayList<InvitationDto> invitationDtos) {
        this.invitationDtos = invitationDtos;
    }

    public HashMap<FriendInfoDto, ChatDto> getUserFriendsAndChatDto() {
        return userFriendsAndChatDto;
    }

    public void setUserFriendsAndChatDto(HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto) {
        this.userFriendsAndChatDto = userFriendsAndChatDto;
    }

    @Override
    public String toString() {
        return "ChatScreenDto{" +
                "userDto=" + userDto +
                ", invitationDtos=" + invitationDtos +
                ", userFriendsAndChatDto=" + userFriendsAndChatDto +
                '}';
    }
}
