package iti.jets.app.server.Mappers;

import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.enums.StatusEnum;

public class FriendInfoDtoMapper {
    public static FriendInfoDto userToFriend (User user){
        return(new FriendInfoDto(user.getDisplayName(),user.getPicture(),user.getId(),user.getMode(),user.getStatus()));

    }
}
