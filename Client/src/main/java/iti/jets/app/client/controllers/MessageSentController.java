package iti.jets.app.client.controllers;

import iti.jets.app.shared.DTOs.MessageDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class MessageSentController implements Initializable {
    @FXML
    public Circle img;
    @FXML
    public Text txt;
    @FXML
    public TextFlow txtFlow;

    @FXML
    public Label timeStamp;

    public void setData(MessageDto msg , Image userImg) {
        //Image image = new Image(getClass().getResourceAsStream(user.getImgSrc()));

        txt.setStyle(
                "-fx-fill:"        + msg.getFontColor()
                + ";-fx-font-weight:"   + msg.getFontWeight()
                + ";-fx-font-size:"     + msg.getFontSize()
                + ";-fx-font-style:"    + msg.getFontStyle()
                + ";-fx-font-family:\"" + msg.getFontFamily()
                + "\";-fx-underline:"   + msg.getUnderline()
                + ";");
        txt.setText(msg.getMessageContent());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String formattedTime = timeFormat.format(msg.getSentAt());
        timeStamp.setText(formattedTime);
        //txtFlow.setTextAlignment(TextAlignment.LEFT);
        if(userImg != null){
            img.setFill(new ImagePattern(userImg));
            //img.setImage(userImg);
        }

        //connectionStatus.setFill(user.getStatus() == StatusEnum.ONLINE ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //scrlPane.prefWidthProperty().bind(txtFlow.widthProperty());
        //scrlPane.prefHeightProperty().bind(txtFlow.heightProperty());
    }
}
