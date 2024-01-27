package iti.jets.app.shared.DTOs;

import java.io.Serializable;
import java.util.ArrayList;

public class UserFriendsDto implements Serializable {
    private ArrayList<FriendInfoDto> userAllFriends = new ArrayList<>();

    public UserFriendsDto(ArrayList<FriendInfoDto> userAllFriends) {
        this.userAllFriends = userAllFriends;
    }

    public ArrayList<FriendInfoDto> getUserAllFriends() {
        return userAllFriends;
    }

    public void setUserAllFriends(ArrayList<FriendInfoDto> userAllFriends) {
        this.userAllFriends = userAllFriends;
    }
}
