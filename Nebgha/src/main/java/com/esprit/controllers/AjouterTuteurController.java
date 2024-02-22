package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.etudiant;
import com.esprit.models.tuteur;
import com.esprit.services.ServiceEtudiant;
import com.esprit.services.ServiceTuteur;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class AjouterTuteurController {
    private TextField tfid;
    private TextField tfnom;
    private TextField tfprenom;
    private TextField tfemail;
    private TextField tfpass;
    private TextField tfrole;
    private TextField tfdomaine;
    private TextField tfspecialite;
    private TextField tfexperience;

    /*String nom, String prenom,
    String email,
    String password, Role role,
    String domaine, String experience)*/
    public void AjouterEtudiant(){
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
