package iti.jets.app.shared.Interfaces;

import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.DTOs.UserLoginDto;


public interface LoginService {
    UserDto login(UserLoginDto userLoginDto);
}
