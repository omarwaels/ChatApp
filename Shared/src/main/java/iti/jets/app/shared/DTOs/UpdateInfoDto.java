package iti.jets.app.shared.DTOs;

import java.io.Serializable;
import java.util.Date;

public class UpdateInfoDto implements Serializable {
    private String fieldName;
    private String newValue;
    private int userId;

    private Date dob;

    public UpdateInfoDto(String fieldName, String newValue, int userId) {
        this.fieldName = fieldName;
        this.newValue = newValue;
        this.userId = userId;
    }

    public UpdateInfoDto() {
    }

    public UpdateInfoDto(Date dob, int userId) {
        this.fieldName = fieldName;
        this.dob = dob;
        this.userId = userId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
