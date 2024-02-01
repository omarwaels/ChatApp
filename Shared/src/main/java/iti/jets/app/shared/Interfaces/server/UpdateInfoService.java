package iti.jets.app.shared.Interfaces.server;

import iti.jets.app.shared.DTOs.UpdateInfoDto;

import java.rmi.Remote;

public interface UpdateInfoService extends Remote {
    int updateField(UpdateInfoDto updateInfoDto) throws Exception;

    int updateDOB(UpdateInfoDto updateInfoDto) throws Exception;

    int updateImage(int userId, byte[] image) throws Exception;
}
