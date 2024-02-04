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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageSentController implements Initializable {
    @FXML
    public ImageView img;
    @FXML
    public Text txt;
    @FXML
    public TextFlow txtFlow;


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
        //txtFlow.setTextAlignment(TextAlignment.LEFT);
        if(userImg != null){
            img.setImage(userImg);
        }

        //connectionStatus.setFill(user.getStatus() == StatusEnum.ONLINE ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //scrlPane.prefWidthProperty().bind(txtFlow.widthProperty());
        //scrlPane.prefHeightProperty().bind(txtFlow.heightProperty());
    }
}
