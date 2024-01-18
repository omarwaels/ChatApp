package iti.jets.app.shared.Interfaces;

import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.models.entities.User;

public interface LoginService {
    User login(UserLoginDto userLoginDto);
}
