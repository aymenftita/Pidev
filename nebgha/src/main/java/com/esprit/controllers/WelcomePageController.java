package com.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePageController {

    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;

    public void SwitchToAfficherMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AfficherMessage");
    }

    public void SwitchToAfficherGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AfficherGroupe");
    }

    public void SwitchToAfficherReclamation(ActionEvent actionEvent ) throws IOException {
        ss.SwitchScene(event,"AfficherReclamation");
    }
}
