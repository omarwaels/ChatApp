package iti.jets.app.server.Mappers;

import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.server.models.entities.User;

public class LoginDtoMapper {
    public static User loginDtoToUser(UserLoginDto userLoginDto) {
        return new User.UserBuilder().setPhoneNumber(userLoginDto.getPhoneNumber()).setPassword(userLoginDto.getPassword()).build();
    }

    public static UserLoginDto userToLoginDto(User user) {
        return new UserLoginDto(user.getPhoneNumber(), user.getPassword());
    }
}
