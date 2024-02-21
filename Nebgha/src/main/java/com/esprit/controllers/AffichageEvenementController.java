package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Date;

public class AffichageEvenementController {

    @FXML
    private Label ttCreatorId;

    @FXML
    private Label ttDate;

    @FXML
    private Label ttDescription;

    @FXML
    private Label ttDuree;

    @FXML
    private Label ttHeure;

    @FXML
    private Label ttLieuId;

    @FXML
    private Label ttNom;

    public void setTtCreatorId(int creatorId) {
        ttCreatorId.setText(Integer.toString(creatorId));
    }

    public void setTtNom(String nom) {
        ttNom.setText(nom);
    }
    public void setTtDate (Date date) {
        ttDate.setText(String.valueOf(date));
    }
    public void setTtHeure (int heure) {
        ttHeure.setText(Integer.toString(heure));
    }
    public void setTtDuree (int duree) {
        ttDuree.setText(Integer.toString(duree));
    }
    public void setTtLieuId (int lieuId) {
        ttLieuId.setText(Integer.toString(lieuId));
    }
    public void setTtDescription(String description) {
        ttDescription.setText(description);
    }
}
