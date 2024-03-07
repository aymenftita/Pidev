package com.esprit.controllers.ReclamationGroupeControllers.messageControllers;

import com.esprit.services.ReclamationGroupServices.MessageService;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class ModifierMessageController {

    MessageService ms = new MessageService();

    @FXML
    public TextField tfidgroupe;
    @FXML
    public TextField tftext;
    @FXML
    public TextField tfidmessage;
    @FXML
    public DatePicker tfdate;

    @FXML
    private void UpdateMessage(){

        /*ms.modifier(new Message(
                Integer.parseInt(tfidmessage.getText()),
                Integer.parseInt(tfidgroupe.getText()),
                tfdate.getValue().toString(),
                tftext.getText()));*/
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message Modifié");
        alert.setContentText("Message Modifié !");
        alert.show();
        
    }


}
