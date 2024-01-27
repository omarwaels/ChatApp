package iti.jets.app.server.Mappers;

import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.UserFriendsDto;

import java.util.ArrayList;

public class UserArrayMapper {

    public static UserFriendsDto userArrToFriendsArr (ArrayList<User> users){
        ArrayList<FriendInfoDto> userFriendsArr = new ArrayList<>();
        for(User user : users ){
            userFriendsArr.add(FriendInfoDtoMapper.userToFriend(user));
        }
        return new UserFriendsDto(userFriendsArr);
    }
}
