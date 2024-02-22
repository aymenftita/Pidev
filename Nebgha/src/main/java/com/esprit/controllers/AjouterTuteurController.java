package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.etudiant;
import com.esprit.models.tuteur;
import com.esprit.services.ServiceEtudiant;
import com.esprit.services.ServiceTuteur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class AjouterTuteurController {
    @FXML
    private TextField tfid;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfpass;
    @FXML
    private TextField tfrole;
    @FXML
    private TextField tfdomaine;
    @FXML
    private TextField tfspecialite;
    @FXML
    private TextField tfexperience;


    public void AjouterTuteur(){
        ServiceTuteur se = new ServiceTuteur();
        se.ajouter(new tuteur(
                Integer.parseInt(tfid.getText()),
                tfnom.getText(),
                tfprenom.getText(),
                tfemail.getText(),
                tfpass.getText(),
                Role.Tuteur,
                tfdomaine.getText(),
                tfexperience.getText()
        ));
    }

    public void SwitchScene(ActionEvent event, String fxmlfile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/"+fxmlfile+".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage Stage = new Stage();
        Stage.setScene(scene);
        Stage.show();
    }
}
