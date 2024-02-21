package com.esprit.controllers.groupeControllers;

import com.esprit.models.Groupe;
import com.esprit.models.Utilisateur;
import com.esprit.services.GroupeService;
import com.esprit.services.UtilisateurService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjouterGroupeController {
    @FXML
    public ComboBox<Utilisateur> tfcreateur_g;
    @FXML
    public TextField tftitre_g;
    @FXML
    public TextField tfdescription_g;

    UtilisateurService us = new UtilisateurService();
    ObservableList<Utilisateur> utilisateur = FXCollections.observableArrayList(us.afficher());
    @FXML
    void addGroupe(ActionEvent event) throws IOException {
        GroupeService ps = new GroupeService();
        ps.ajouter(new Groupe(tfcreateur_g.getValue() , tftitre_g.getText(),tfdescription_g.getText() ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Groupe ajoutée");
        alert.setContentText("Groupe ajoutée !");
        alert.show();

    }

    public void initialize(){

        tfcreateur_g.setItems(utilisateur);
    }



}
