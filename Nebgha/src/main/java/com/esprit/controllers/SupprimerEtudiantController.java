package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.admin;
import com.esprit.models.etudiant;
import com.esprit.services.ServiceAdmin;
import com.esprit.services.ServiceEtudiant;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SupprimerEtudiantController {

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
    private TextField tfniveau;
    @FXML
    private TextField tfspecialite;

    public void SupprimerEtudiant(){
        ServiceEtudiant sa = new ServiceEtudiant();
        sa.supprimer(
                new etudiant(
                        Integer.parseInt(tfid.getText()),
                        tfnom.getText(),
                        tfprenom.getText(),
                        tfemail.getText(),
                        tfpass.getText(),
                        Role.etudiant,
                        Integer.parseInt(tfniveau.getText()),
                        tfspecialite.getText()
                )
        );
    }

}
