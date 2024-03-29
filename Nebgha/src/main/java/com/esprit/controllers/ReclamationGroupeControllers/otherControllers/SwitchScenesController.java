package com.esprit.controllers.ReclamationGroupeControllers.otherControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class SwitchScenesController {
    public void SwitchScene(ActionEvent event,String fxmlfile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamationEtGroupesChatRessources/Interfaces/" +fxmlfile+".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage Stage = new Stage();
        Stage.setScene(scene);
        Stage.show();
    }

    public void SwitchScene2(ActionEvent event, String fxmlfile, TextField t) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamationEtGroupesChatRessources/Interfaces/" +fxmlfile+".fxml"));
        Parent root = loader.load();
        t.getScene().setRoot(root);
    }

    public void SwitchScene3(MouseEvent event, String fxmlfile, TextField t) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamationEtGroupesChatRessources/Interfaces/" +fxmlfile+".fxml"));
        Parent root = loader.load();
        t.getScene().setRoot(root);
    }



}


