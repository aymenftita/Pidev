package com.esprit.controllers.groupeControllers;

import com.esprit.controllers.otherControllers.SwitchScenesController;
import com.esprit.models.Groupe;
import com.esprit.models.Utilisateur;
import com.esprit.services.GroupeService;
import com.esprit.services.Session;
import com.esprit.services.UtilisateurService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class AjouterGroupeController {

    @FXML
    public TextField tftitre_g;
    @FXML
    public TextField tfdescription_g;
    public Label csTitre;
    public Label csDesc;
    public ImageView image;

    UtilisateurService us = new UtilisateurService();
    Utilisateur u = us.rechercheUtilisateur(Session.getUserId());
    //ObservableList<Utilisateur> utilisateur = FXCollections.observableArrayList(us.afficher());
    @FXML
    void addGroupe(ActionEvent event) throws IOException {
        if(tftitre_g.getText().trim().isEmpty()){
            csTitre.setText("Le champ Titre est vide");
            csDesc.setText("");
        }else if(tfdescription_g.getText().trim().isEmpty()){
            csTitre.setText("");
            csDesc.setText("Le champ Description est vide");
        }else{
            GroupeService ps = new GroupeService();
            ps.ajouter(new Groupe(u , tftitre_g.getText(),tfdescription_g.getText() ));
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Groupe ajoutée");
            alert.setContentText("Groupe ajoutée !");
            alert.show();*/
            SwitchScenesController ss = new SwitchScenesController();
            ss.SwitchScene2(event,"testFlow",tftitre_g);
        }


    }



    public void initialize(){



        //tfcreateur_g.setItems(utilisateur);
    }

    @FXML
    private void handleImageViewClick() throws IOException {
        SwitchScenesController ss = new SwitchScenesController();
        ActionEvent event=null;
        ss.SwitchScene2(event,"testFlow",tftitre_g);
    }



}
