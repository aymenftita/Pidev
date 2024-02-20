package com.esprit.controllers;

import com.esprit.models.Message;
import com.esprit.services.MessageService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterMessageController {

    @FXML
    public DatePicker dpAjouterMessage;
    @FXML
    public TextField tfAjouterMessage;
    @FXML
    public TextArea taAjouterMessage;


    @FXML
    void addMessage(ActionEvent event) throws IOException {

        MessageService ps = new MessageService();
            ps.ajouter(new Message(Integer.parseInt(tfAjouterMessage.getText()) , dpAjouterMessage.getValue().toString(), taAjouterMessage.getText())) ;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message ajoutée");
            alert.setContentText("Message ajoutée !");
            alert.show();

    }



}
