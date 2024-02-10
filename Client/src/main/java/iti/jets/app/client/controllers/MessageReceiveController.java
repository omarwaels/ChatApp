package iti.jets.app.client.controllers;

import iti.jets.app.shared.DTOs.MessageDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class MessageReceiveController implements Initializable {
    @FXML
    public Circle img;

    @FXML
    public Text txt;
    @FXML
    public Label timeStamp;
    @FXML
    public TextFlow txtFlow;

    public void setData(MessageDto msg , Image userImage) {
        txt.setStyle(
                        "-fx-fill:"             + msg.getFontColor()
                        + ";-fx-font-weight:"   + msg.getFontWeight()
                        + ";-fx-font-size:"     + msg.getFontSize()
                        + ";-fx-font-style:"    + msg.getFontStyle()
                        + ";-fx-font-family:\"" + msg.getFontFamily()
                        + "\";-fx-underline:"   + msg.getUnderline()
                        + ";");

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String formattedTime = timeFormat.format(msg.getSentAt());
        timeStamp.setText(formattedTime);
        txt.setText(msg.getMessageContent());
        if(userImage != null){
            img.setFill(new ImagePattern(userImage));

        }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
