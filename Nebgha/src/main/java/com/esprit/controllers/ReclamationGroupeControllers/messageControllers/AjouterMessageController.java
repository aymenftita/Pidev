package com.esprit.controllers.ReclamationGroupeControllers.messageControllers;

import com.esprit.models.ReclamationGroupModels.Message;
import com.esprit.services.ReclamationGroupServices.MessageService;
import com.esprit.services.ReclamationGroupServices.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    try{
        MessageService ps = new MessageService();
            ps.ajouter(new Message(Integer.parseInt(tfAjouterMessage.getText()) , dpAjouterMessage.getValue().toString(), taAjouterMessage.getText(),Session.getUserId(),0)) ;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message ajoutée");
            alert.setContentText("Message ajoutée !");
            alert.show();
    }catch(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(e.getMessage());
        alert.show();
    }

    }



}
