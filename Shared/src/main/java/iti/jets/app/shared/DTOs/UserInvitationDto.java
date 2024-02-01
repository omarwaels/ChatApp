package iti.jets.app.shared.DTOs;
import lombok.*;
@Data
@Getter
@Setter

@NoArgsConstructor
public class UserInvitationDto {
    private String userName;
    private String phoneNumber;
    private int userId;
    private byte[] image;

    public UserInvitationDto(String userName, String phoneNumber, int userId, byte[] image) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.image = image;
    }
}
