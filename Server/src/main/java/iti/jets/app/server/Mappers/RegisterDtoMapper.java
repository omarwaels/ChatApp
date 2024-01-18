package iti.jets.app.server.Mappers;

import iti.jets.app.shared.DTOs.UserRegisterDto;
import iti.jets.app.shared.models.entities.User;

public class RegisterDtoMapper {

    public static User registerDtoToUser(UserRegisterDto userRegisterDto) {
        return new User.UserBuilder().setPhoneNumber(userRegisterDto.getPhoneNumber()).
                setPassword(userRegisterDto.getPassword()).setPicture(userRegisterDto.getPicture())
                .setEmail(userRegisterDto.getEmail()).setDisplayName(userRegisterDto.getDisplayName())
                .setGender(userRegisterDto.getGender()).setCountry(userRegisterDto.getCountry())
                .setBio(userRegisterDto.getBio()).setDateOfBirth(userRegisterDto.getDateOfBirth()).build();
    }

    public static UserRegisterDto userToRegisterDto(User user) {
        UserRegisterDto userRegisterDto = new UserRegisterDto(user.getPhoneNumber(), user.getPassword());
        userRegisterDto.setBio(user.getBio());
        userRegisterDto.setCountry(user.getCountry());
        userRegisterDto.setDisplayName(user.getDisplayName());
        userRegisterDto.setEmail(user.getEmail());
        userRegisterDto.setGender(user.getGender());
        userRegisterDto.setPicture(user.getPicture());
        userRegisterDto.setDateOfBirth(user.getDateOfBirth());
        return userRegisterDto;
    }
}
