package com.esprit.controllers.reponse;

import com.esprit.models.Reponse;
import com.esprit.models.Sujet;
import com.esprit.services.reponseService;
import com.esprit.services.sujetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AjoutReponseController {
    @FXML
    private DatePicker dpDateReponse;

    @FXML
    private TextField tfAuteurID;

    @FXML
    private TextArea tfContenuReponse;

    @FXML
    private TextField tfQuestionID;

    @FXML
    private TextField tfSujetID;

    @FXML
    void ajouterReponse(ActionEvent event) {
        reponseService rs = new reponseService();
        rs.ajouter(new Reponse(0, Integer.parseInt(tfAuteurID.getText()),
                Integer.parseInt(tfQuestionID.getText()), tfContenuReponse.getText(),
                Date.valueOf(dpDateReponse.getValue()), Integer.parseInt(tfSujetID.getText())));
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout Question");
        alertAjout.setHeaderText("Succées!");
        alertAjout.setContentText("Question ajouté!");
        alertAjout.show();

    }

    @FXML
    void menuAfficherReponse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesReponse/AfficherReponse.fxml"));
        Parent root = loader.load();
        tfAuteurID.getScene().setRoot(root);
        AfficherReponseController asc = loader.getController();

    }

    @FXML
    void menuAjoutReponse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesReponse/AjoutReponse.fxml"));
        Parent root = loader.load();
        tfAuteurID.getScene().setRoot(root);

    }

    @FXML
    void menuModifierReponse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesReponse/ModifierReponse.fxml"));
        Parent root = loader.load();
        tfAuteurID.getScene().setRoot(root);
        ModifierReponseController asc = loader.getController();

    }

    @FXML
    void menuSupprimerReponse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesReponse/SupprimerReponse.fxml"));
        Parent root = loader.load();
        tfAuteurID.getScene().setRoot(root);

    }

}
