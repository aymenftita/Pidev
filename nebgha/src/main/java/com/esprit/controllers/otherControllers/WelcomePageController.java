package com.esprit.controllers.otherControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;

public class WelcomePageController {
    @FXML
    public AnchorPane A;
    public Button bt;
    public TextField tf;

    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;

    public void SwitchToAfficherMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherMessage",tf);
    }

    public void SwitchToAfficherGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherGroupe",tf);
    }

    public void SwitchToAfficherReclamation(ActionEvent actionEvent ) throws IOException {
       ss.SwitchScene2(event,"AfficherReclamation",tf);

    }



}
