package iti.jets.app.server.Mappers;

import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.UserDto;


public class UserDtoMapper {
    public static User UserDtoToUser(UserDto userDto) {
        return new User.UserBuilder().setPhoneNumber(userDto.getPhoneNumber())
                .setPassword(userDto.getPassword()).setPicture(userDto.getPicture())
                .setDisplayName(userDto.getDisplayName()).setBio(userDto.getBio())
                .setId(userDto.getId()).setCountry(userDto.getCountry())
                .setEmail(userDto.getEmail()).setGender(userDto.getGender())
                .setDateOfBirth(userDto.getDateOfBirth()).build();

    }

    public static UserDto UserToUserDto(User user) {
        return new UserDto.UserDtoBuilder().setPhoneNumber(user.getPhoneNumber())
                .setPassword(user.getPassword()).setPicture(user.getPicture())
                .setDisplayName(user.getDisplayName()).setBio(user.getBio())
                .setId(user.getId()).setCountry(user.getCountry())
                .setEmail(user.getEmail()).setGender(user.getGender())
                .setDateOfBirth(user.getDateOfBirth())
                .setStatus(user.getStatus()).setMode(user.getMode())
                .build();

    }
}
