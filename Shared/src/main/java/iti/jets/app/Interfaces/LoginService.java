package iti.jets.app.Interfaces;

import iti.jets.app.DTOs.UserLoginDto;
import iti.jets.app.models.entities.User;

public interface LoginService {
    User login(UserLoginDto userLoginDto);
}
