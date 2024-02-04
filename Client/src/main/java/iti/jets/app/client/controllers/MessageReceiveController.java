package iti.jets.app.client.controllers;

import iti.jets.app.shared.DTOs.MessageDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageReceiveController implements Initializable {
    @FXML
    public Circle img;

    @FXML
    public Text txt;


    @FXML
    public TextFlow txtFlow;

    public void setData(MessageDto msg , Image userImage) {
        //Image image = new Image(getClass().getResourceAsStream(user.getImgSrc()));
        txt.setStyle(
                        "-fx-fill:"             + msg.getFontColor()
                        + ";-fx-font-weight:"   + msg.getFontWeight()
                        + ";-fx-font-size:"     + msg.getFontSize()
                        + ";-fx-font-style:"    + msg.getFontStyle()
                        + ";-fx-font-family:\"" + msg.getFontFamily()
                        + "\";-fx-underline:"   + msg.getUnderline()
                        + ";");

        txt.setText(msg.getMessageContent());
        if(userImage != null){
            img.setFill(new ImagePattern(userImage));
            //img.setImage(userImage);
        }

        //connectionStatus.setFill(user.getStatus() == StatusEnum.ONLINE ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
