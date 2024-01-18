package iti.jets.app.shared.Interfaces;

import iti.jets.app.shared.DTOs.UserRegisterDto;

public interface RegisterService {
    int register(UserRegisterDto userRegisterDto);
}
