package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.etudiant;
import com.esprit.models.tuteur;
import com.esprit.services.ServiceEtudiant;
import com.esprit.services.ServiceTuteur;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SupprimerTuteurController {

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

    public void SupprimerTuteur(){
        ServiceTuteur sa = new ServiceTuteur();
        sa.supprimer(
                new tuteur(
                        Integer.parseInt(tfid.getText()),
                        tfnom.getText(),
                        tfprenom.getText(),
                        tfemail.getText(),
                        tfpass.getText(),
                        Role.Tuteur,
                        tfdomaine.getText(),
                        tfexperience.getText()
                )
        );
    }
}
