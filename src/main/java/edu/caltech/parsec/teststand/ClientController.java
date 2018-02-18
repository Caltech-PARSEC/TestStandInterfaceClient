package edu.caltech.parsec.teststand;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }

    @FXML protected void handleCloseClicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML protected void displayAboutInformation(ActionEvent event) {
        System.exit(0);
    }



}
