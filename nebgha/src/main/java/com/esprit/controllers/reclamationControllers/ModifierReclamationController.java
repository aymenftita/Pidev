package com.esprit.controllers.reclamationControllers;

import com.esprit.models.Reclamation;
import com.esprit.models.Status;
import com.esprit.models.Utilisateur;
import com.esprit.services.ReclamationService;
import com.esprit.services.UtilisateurService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ModifierReclamationController {
    public TextField tfDescription;
    public TextField tfSujet;
    public DatePicker tfDate;
    public ComboBox<Utilisateur> tfUserId;
    public TextField tfpriorite;
    public TextField tfIdREclamation;
    public ComboBox<Status> cbstatus;

    UtilisateurService us = new UtilisateurService();
    private ObservableList<Utilisateur> users = FXCollections.observableArrayList(us.afficher());
    private ObservableList<Status> status = FXCollections.observableArrayList(
            Status.envoyé,
            Status.EnReponse,
            Status.Traité,
            Status.EnCourDeTraitement
    );

        public void initialize() {
            cbstatus.setItems(status);
            tfUserId.setItems(users);
        }

    public void UpdateReclamation(ActionEvent actionEvent) {

        ReclamationService ps = new ReclamationService();
        ps.modifier(new Reclamation(Integer.parseInt(
                tfIdREclamation.getText()),
                tfUserId.getValue() ,
                tfDate.getValue().toString(),
                tfSujet.getText(),
                tfDescription.getText(),
                cbstatus.getValue().toString(),
                Integer.parseInt(tfpriorite.getText()),"admin" ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reclamation Modifié");
        alert.setContentText("Reclamation Modifié !");
        alert.show();
    }

}
