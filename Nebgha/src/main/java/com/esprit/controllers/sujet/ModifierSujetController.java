package com.esprit.controllers.sujet;

import com.esprit.controllers.InterfacesAdminController;
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

public class ModifierSujetController {

    @FXML
    private TextArea tfModifDescriptionSujet;

    @FXML
    private TextField tfModifIdSujet;

    @FXML
    private TextArea tfModifReglesSujet;

    @FXML
    private TextField tfModifSujetTitre;

    private Sujet sujetToModify; // Store the sujet to be modified

    public void setSujetToModify(Sujet sujet) {
        this.sujetToModify = sujet;
        populateTextFields(); // Populate the text fields with the sujet's data
    }

    private void populateTextFields() {
        if (sujetToModify != null) {
            tfModifIdSujet.setText(String.valueOf(sujetToModify.getId()));
            tfModifSujetTitre.setText(sujetToModify.getTitre());
            tfModifDescriptionSujet.setText(sujetToModify.getDesc());
            tfModifReglesSujet.setText(sujetToModify.getRegles());
        } else {
            // Clear the text fields if no sujet is set
            tfModifIdSujet.clear();
            tfModifSujetTitre.clear();
            tfModifDescriptionSujet.clear();
            tfModifReglesSujet.clear();
        }
    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfModifSujetTitre.getScene().setRoot(root);
        InterfacesAdminController iac = loader.getController();
    }

    @FXML
    void modifierSujet(ActionEvent event) {
        sujetService SS = new sujetService();
        SS.modifier(new Sujet(Integer.parseInt(tfModifIdSujet.getText()), tfModifSujetTitre.getText(), tfModifDescriptionSujet.getText(), tfModifReglesSujet.getText()));
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Sujet");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Sujet modifié!");
        alertModif.show();

    }

}
