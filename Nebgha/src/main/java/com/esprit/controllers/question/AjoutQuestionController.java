package com.esprit.controllers.question;

import com.esprit.models.Sujet;
import com.esprit.services.sujetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjoutQuestionController {

    @FXML
    private TextArea tfDescriptionSujet;

    @FXML
    private TextArea tfReglesSujet;

    @FXML
    private TextField tfSujetTitre;

    @FXML
    void ajouterSujet(ActionEvent event) {

        sujetService SS = new sujetService();
        SS.ajouter(new Sujet(11, tfSujetTitre.getText(), tfDescriptionSujet.getText(), tfReglesSujet.getText()));
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout Sujet");
        alertAjout.setHeaderText("Succées!");
        alertAjout.setContentText("Sujet ajouté!");
        alertAjout.show();

    }

}
