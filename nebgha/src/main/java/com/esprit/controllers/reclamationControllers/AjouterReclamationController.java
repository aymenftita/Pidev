package com.esprit.controllers.reclamationControllers;
import com.esprit.models.Reclamation;
import com.esprit.models.Status;
import com.esprit.models.Utilisateur;
import com.esprit.services.ReclamationService;
import com.esprit.services.UtilisateurService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.io.IOException;


public class AjouterReclamationController {


    public ChoiceBox<String> cb ;
    @FXML
    public TextField tfP;
    public ComboBox<Utilisateur> cbUsers;
    @FXML
    private TextField tfid;

    @FXML
    private TextField tfSujet;

    @FXML
    private TextField tfDescription;
    @FXML
    private DatePicker tfDate;
    UtilisateurService us = new UtilisateurService();
    private ObservableList<Utilisateur> users = FXCollections.observableArrayList(us.afficher());

    @FXML
    void addReclamation(ActionEvent event) throws IOException {

        ReclamationService ps = new ReclamationService();
        ps.ajouter(new Reclamation(
                cbUsers.getValue(),
                tfDate.getValue().toString(),
                tfSujet.getText(),
                tfDescription.getText(),
                cb.getValue().toString(),
                Integer.parseInt(tfP.getText()),"admin" ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personne ajoutée");
        alert.setContentText("Personne ajoutée !");
        alert.show();

    }

    public void initialize(){

        cb.setItems(FXCollections.observableArrayList(
                Status.envoyé.toString(),
                Status.EnCourDeTraitement.toString(),
                Status.Traité.toString(),
                Status.EnReponse.toString()));

        cbUsers.setItems(FXCollections.observableArrayList(users));
        cbUsers.setCellFactory(listView -> new ListCell<Utilisateur>() {
            @Override
            protected void updateItem(Utilisateur user, boolean empty) {
                super.updateItem(user, empty);
                if (user != null) {
                    setText(String.valueOf(user.getNom()));
                } else {
                    setText(null);
                }
            }
        });
    }






}
