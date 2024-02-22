package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.admin;
import com.esprit.models.etudiant;
import com.esprit.services.ServiceAdmin;
import com.esprit.services.ServiceEtudiant;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class AjouterEtudiantController {
    private TextField tfid;
    private TextField tfnom;
    private TextField tfprenom;
    private TextField tfemail;
    private TextField tfpass;
    private TextField tfrole;
    private TextField tfniveau;
    private TextField tfspecialite;


    public void AjouterEtudiant(){
        ServiceEtudiant se = new ServiceEtudiant();
        se.ajouter(new etudiant(
                Integer.parseInt(tfid.getText()),
                tfnom.getText(),
                tfprenom.getText(),
                tfemail.getText(),
                tfpass.getText(),
                Role.etudiant,
                Integer.parseInt(tfniveau.getText()),
                tfspecialite.getText()
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
