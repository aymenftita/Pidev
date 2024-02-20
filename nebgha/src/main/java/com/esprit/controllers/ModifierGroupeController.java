package com.esprit.controllers;

import com.esprit.models.Groupe;
import com.esprit.services.GroupeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ModifierGroupeController {
    @FXML
    public TextField tfCreateur;
    @FXML
    public TextField tfTitre;
    @FXML
    public TextField tfDescription;
    @FXML
    public TextField tfIdGroupe;

    @FXML
    public void UpdateGroupe(ActionEvent actionEvent) {

        GroupeService ps = new GroupeService();
        ps.modifier(new Groupe(Integer.parseInt(tfIdGroupe.getText()),Integer.parseInt(tfCreateur.getText()) , tfTitre.getText(), tfDescription.getText() ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Groupe Modifié");
        alert.setContentText("Gorupe "+tfIdGroupe.getText()+" "+tfTitre.getText()+" Modifié");
        alert.show();
    }

    public void initialize(){
        AfficherGroupeController gc = new AfficherGroupeController();
        /*tfTitre.setText(gc.getSelectedGroupe().getTitre());
        tfCreateur.setText("dgdg");
        tfIdGroupe.setText("id");
        tfDescription.setText(gc.getSelectedGroupe().getDescription());*/

    }


}
