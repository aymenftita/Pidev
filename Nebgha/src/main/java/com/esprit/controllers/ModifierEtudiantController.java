package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.etudiant;
import com.esprit.services.ServiceEtudiant;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ModifierEtudiantController {
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


    public void modifierEtudiant(){
        ServiceEtudiant se = new ServiceEtudiant();
        se.modifier(new etudiant(
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
}
