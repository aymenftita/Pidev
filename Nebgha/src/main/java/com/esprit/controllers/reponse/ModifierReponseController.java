package com.esprit.controllers.reponse;

import com.esprit.controllers.InterfacesAdminController;
import com.esprit.models.Reponse;
import com.esprit.services.reponseService;
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

public class ModifierReponseController {

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

    private Reponse reponseToModify;

    public void setReponseToModify(Reponse reponse) {
        //chargement d'instance à modifier
        this.reponseToModify = reponse;
        populateTextFields();
    }

    private void populateTextFields() {
        //chargement des champs de textes
        if (reponseToModify != null) {
            tfAuteurID.setText(String.valueOf(reponseToModify.getAuteur_id()));
            tfContenuReponse.setText(reponseToModify.getContenu());
            tfQuestionID.setText(String.valueOf(reponseToModify.getQuestion_id()));
            tfSujetID.setText(String.valueOf(reponseToModify.getSujet_id()));
            dpDateReponse.setValue(reponseToModify.getDate().toLocalDate());

        } else {
            // Si pas de réponse choisi, vider les champs
            tfAuteurID.clear();
            tfContenuReponse.clear();
            tfQuestionID.clear();
            tfSujetID.clear();
            dpDateReponse.setValue(null);
        }
    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfSujetID.getScene().setRoot(root);
    }

    @FXML
    void modifierReponse(ActionEvent event) {
        //Création du service et modification d'entité
        reponseService RS = new reponseService();
        RS.modifier(new Reponse(0, Integer.parseInt(tfAuteurID.getText()),
                Integer.parseInt(tfQuestionID.getText()), tfContenuReponse.getText(),
                Date.valueOf(dpDateReponse.getValue()), Integer.parseInt(tfSujetID.getText())));

        //Message de confirmation
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Réponse");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Réponse modifié!");
        alertModif.show();

    }

}
