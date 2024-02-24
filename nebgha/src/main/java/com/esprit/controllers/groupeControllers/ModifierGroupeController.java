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

public class ModifierGroupeController {
    @FXML
    public ComboBox<Utilisateur> tfCreateur;
    @FXML
    public TextField tfTitre;
    @FXML
    public TextField tfDescription;
    @FXML
    public TextField tfIdGroupe;

    UtilisateurService us = new UtilisateurService();
    ObservableList<Utilisateur> utilisateur = FXCollections.observableArrayList(us.afficher());

    @FXML
    public void UpdateGroupe(ActionEvent actionEvent) {

        GroupeService ps = new GroupeService();
        ps.modifier(new Groupe(Integer.parseInt(tfIdGroupe.getText()),tfCreateur.getValue() , tfTitre.getText(), tfDescription.getText() ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Groupe Modifié");
        alert.setContentText("Gorupe "+tfIdGroupe.getText()+" "+tfTitre.getText()+" Modifié");
        alert.show();
    }

    public void initialize(){
        AfficherGroupeController ag =new AfficherGroupeController();
        tfTitre.setText(ag.selectedGroupe().getTitre());

        tfCreateur.setItems(utilisateur);
    }



}
