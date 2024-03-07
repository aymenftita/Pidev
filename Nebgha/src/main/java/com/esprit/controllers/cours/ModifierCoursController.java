package com.esprit.controllers.cours;

import com.esprit.models.cours.Cours;
import com.esprit.services.cours.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ModifierCoursController {
    public Label lbIdUIC;
    @FXML
    private TextArea tfModifContenuCours;

    @FXML
    private TextField tfModifIdCours;

    @FXML
    private TextArea tfModifDescriptionCours;

    @FXML
    private TextField tfModifCoursTitre;

    @FXML
    void menuAfficherCours(ActionEvent event) throws IOException {

    }

    @FXML
    void menuAjoutCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCours.fxml"));
        Parent root = loader.load();
        tfModifIdCours.getScene().setRoot(root);
        AjoutModuleController asc = loader.getController();

    }

    @FXML
    void menuModifierCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/ModifierCours.fxml"));
        Parent root = loader.load();
        tfModifIdCours.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerCours(ActionEvent event) {

    }

    @FXML
    void modifierCours(ActionEvent event) {
        if(tfModifIdCours.getText().isEmpty()){
            lbIdUIC.setText("l'id ne peut pqs etre vide");
        }else {
            CoursService CS = new CoursService();
            CS.modifier(new Cours(Integer.parseInt(tfModifIdCours.getText()), tfModifCoursTitre.getText(), tfModifDescriptionCours.getText(), tfModifContenuCours.getText(),1));
            Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
            alertModif.setTitle("Modification Cours");
            alertModif.setHeaderText("Succées!");
            alertModif.setContentText("Cours modifié!");
            alertModif.show();
        }


    }
}
