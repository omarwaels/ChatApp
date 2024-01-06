package com.example.client.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author bel sa
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public Label userNameErrorLabel ;
    public Label PasswordErrorLabel ;
    public Label LogInErrorLabel ;
    public TextField userNameTextField ;
    public PasswordField passwordTextField ;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void onLoginSubmit() {
        if(isEmptyField(userNameTextField)){
            userNameErrorLabel.setText("You must enter your user name");
            return;
        }

        if(isEmptyField(passwordTextField)){

            PasswordErrorLabel.setText("You must enter your password");
            return;
        }
    }
    @FXML
    public void onUserNameInput() {
        System.out.println("omar");
        userNameErrorLabel.setText("");
    }
    @FXML
    public void onPasswordInput() {
        PasswordErrorLabel.setText("");
    }
    public static boolean isEmptyField(TextField textField){
        if(textField.getText().equals("")){
            return true;
        }
        return false;
    }
    
}
