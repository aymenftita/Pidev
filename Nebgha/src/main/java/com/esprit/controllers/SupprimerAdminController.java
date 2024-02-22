package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.admin;
import com.esprit.services.ServiceAdmin;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SupprimerAdminController {
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

    public void SupprimerAdmin(){
        ServiceAdmin sa = new ServiceAdmin();
        sa.supprimer(
                new admin(
                        Integer.parseInt(tfid.getText()),
                        tfnom.getText(),
                        tfprenom.getText(),
                        tfemail.getText(),
                        tfpass.getText(),
                        Role.Administrateur
                )
        );
    }
}
