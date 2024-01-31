package iti.jets.app.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CreateGroupController implements Initializable {

    @FXML
    public Button btnCreate;

    @FXML
    public ListView<String> listOfConnections;

    @FXML
    public TextField txtFieldGroupName;
    
    List<String> groupMembers;

    public CreateGroupController() {
        groupMembers = new ArrayList<>();
    }

    @FXML
    public void btnCreateAction(ActionEvent event)
    {
        /**
         *
         * Handle the action of creating the group and validate the
         * **/

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // We MUST have a list of connections of this user. then,  we will fill the (contactsName) list with their name
        List<String> contactsName = Arrays.asList("Omar", "Dola", "Wael", "Maher");

        ObservableList<String> data = FXCollections.observableArrayList(contactsName);
        listOfConnections.setItems(data);

        listOfConnections.setCellFactory(listView -> new ListCell<String>()
        {
            @Override
            protected void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);
                if (empty || item == null)
                {
                    setText(null);
                    setGraphic(null);
                } else
                {
                    CheckBox checkbox = new CheckBox(item);
                    checkbox.setOnAction(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event)
                        {
                            if (!groupMembers.contains(checkbox.getText()))
                                groupMembers.add(checkbox.getText());
                            else
                                groupMembers.remove(checkbox.getText());
                        }
                    });
                    setGraphic(checkbox);
                }
            }
        });
    }
}