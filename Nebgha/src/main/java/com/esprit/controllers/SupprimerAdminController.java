package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.admin;
import com.esprit.services.ServiceAdmin;
import javafx.scene.control.TextField;

public class SupprimerAdminController {
    private TextField tfid;
    private TextField tfnom;
    private TextField tfprenom;
    private TextField tfemail;
    private TextField tfpass;
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
