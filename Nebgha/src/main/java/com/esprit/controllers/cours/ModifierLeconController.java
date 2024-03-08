package com.esprit.controllers.cours;

import com.esprit.models.cours.lecon;
import com.esprit.services.cours.LeconService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifierLeconController {

    public Label lbIdLC;

    @FXML
    private TextArea tfModifContenuLecon;

    @FXML
    private TextField tfModifIdLecon;

    @FXML
    private TextArea tfModifDescriptionLecon;

    @FXML
    private TextField tfModifIdCours;

    @FXML
    private TextField tfModifLeconTitre;

    @FXML
    void menuAfficherLecon(ActionEvent event) throws IOException {

    }

    @FXML
    void menuAjoutLecon(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutLecon.fxml"));
        Parent root = loader.load();
        tfModifIdLecon.getScene().setRoot(root);
        AjoutModuleController asc = loader.getController();

    }

    @FXML
    void menuModifierLecon(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/ModifierLecon.fxml"));
        Parent root = loader.load();
        tfModifIdLecon.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerLecon(ActionEvent event) {

    }

    @FXML
    void modifierLecon(ActionEvent event) {
        if (tfModifIdLecon.getText().isEmpty()){
            lbIdLC.setText("L'id ne doit pas etre vide");
        } else {
            LeconService LS = new LeconService();
            LS.modifier(new lecon(Integer.parseInt(tfModifIdLecon.getText()),tfModifLeconTitre.getText(), tfModifDescriptionLecon.getText(),6, tfModifContenuLecon.getText()));
            Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
            alertModif.setTitle("Modification lecon");
            alertModif.setHeaderText("Succées!");
            alertModif.setContentText("lecon modifié!");
            alertModif.show();

        }

    }
    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceTuteurCours.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
}
